package stroom.proxy.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stroom.datafeed.server.MetaMapFilter;
import stroom.datafeed.server.RequestHandler;
import stroom.feed.MetaMap;
import stroom.feed.MetaMapFactory;
import stroom.feed.StroomHeaderArguments;
import stroom.feed.StroomStreamException;
import stroom.proxy.StroomStatusCode;
import stroom.proxy.repo.StroomStreamProcessor;
import stroom.util.io.ByteCountInputStream;
import stroom.util.thread.BufferFactory;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Main entry point to handling proxy requests.
 * <p>
 * This class used the main context and forwards the request on to our
 * dynamic mini proxy.
 */
public class ProxyRequestHandler implements RequestHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyRequestHandler.class);
    private static final Logger RECEIVE_LOG = LoggerFactory.getLogger("receive");
    private static final AtomicInteger concurrentRequestCount = new AtomicInteger(0);

    private final MasterStreamHandlerFactory streamHandlerFactory;
    private final MetaMapFilter metaMapFilter;
    private final LogStream logStream;

    @Inject
    public ProxyRequestHandler(final MasterStreamHandlerFactory streamHandlerFactory,
                               final MetaMapFilterFactory metaMapFilterFactory,
                               final LogStream logStream) {
        this.streamHandlerFactory = streamHandlerFactory;
        this.logStream = logStream;
        metaMapFilter = metaMapFilterFactory.create();
    }

    @Override
    public void handle(final HttpServletRequest request, final HttpServletResponse response) {
        concurrentRequestCount.incrementAndGet();
        try {
            stream(request, response);
        } finally {
            concurrentRequestCount.decrementAndGet();
        }
    }

    private void stream(final HttpServletRequest request, final HttpServletResponse response) {
        int returnCode = HttpServletResponse.SC_OK;

        final long startTimeMs = System.currentTimeMillis();
        final MetaMap metaMap = MetaMapFactory.create(request);

        try {
            final String feedName = metaMap.get(StroomHeaderArguments.FEED);
            if (feedName == null || feedName.trim().isEmpty()) {
                throw new StroomStreamException(StroomStatusCode.FEED_MUST_BE_SPECIFIED);
            }

            try (final ByteCountInputStream inputStream = new ByteCountInputStream(request.getInputStream())) {
                // Test to see if we are going to accept this stream or drop the data.
                if (metaMapFilter.filter(metaMap)) {
                    // Send the data
                    final List<StreamHandler> handlers = streamHandlerFactory.addReceiveHandlers(new ArrayList<>());

                    try {
                        // Set the meta map for all handlers.
                        for (final StreamHandler streamHandler : handlers) {
                            streamHandler.setMetaMap(metaMap);
                        }

                        final byte[] buffer = BufferFactory.create();
                        final StroomStreamProcessor stroomStreamProcessor = new StroomStreamProcessor(metaMap, handlers, buffer, "DataFeedServlet");

                        stroomStreamProcessor.processRequestHeader(request);

                        for (final StreamHandler streamHandler : handlers) {
                            streamHandler.handleHeader();
                        }

                        stroomStreamProcessor.process(inputStream, "");

                        for (final StreamHandler streamHandler : handlers) {
                            streamHandler.handleFooter();
                        }

                    } catch (final Exception e) {
                        for (final StreamHandler streamHandler : handlers) {
                            try {
                                streamHandler.handleError();
                            } catch (final Exception ex) {
                                LOGGER.error(ex.getMessage(), ex);
                            }
                        }

                        throw e;
                    }

                    final long duration = System.currentTimeMillis() - startTimeMs;
                    logStream.log(RECEIVE_LOG, metaMap, "RECEIVE", request.getRequestURI(), returnCode, inputStream.getCount(), duration);

                } else {
                    // Just read the stream in and ignore it
                    final byte[] buffer = BufferFactory.create();
                    while (inputStream.read(buffer) >= 0) {
                        // Ignore data.
                    }
                    returnCode = HttpServletResponse.SC_OK;
                    LOGGER.warn("\"Dropped stream\",{}", CSVFormatter.format(metaMap));

                    final long duration = System.currentTimeMillis() - startTimeMs;
                    logStream.log(RECEIVE_LOG, metaMap, "DROP", request.getRequestURI(), returnCode, inputStream.getCount(), duration);
                }
            }
        } catch (final StroomStreamException e) {
            StroomStreamException.sendErrorResponse(response, e);
            returnCode = e.getStroomStatusCode().getCode();

            LOGGER.warn("\"handleException()\",{},\"{}\"", CSVFormatter.format(metaMap), CSVFormatter.escape(e.getMessage()));

            final long duration = System.currentTimeMillis() - startTimeMs;
            if (StroomStatusCode.FEED_IS_NOT_SET_TO_RECEIVED_DATA.equals(e.getStroomStatusCode())) {
                logStream.log(RECEIVE_LOG, metaMap, "REJECT", request.getRequestURI(), returnCode, -1, duration);
            } else {
                logStream.log(RECEIVE_LOG, metaMap, "ERROR", request.getRequestURI(), returnCode, -1, duration);
            }

        } catch (final Exception e) {
            StroomStreamException.sendErrorResponse(response, e);
            returnCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

            LOGGER.error("\"handleException()\",{}", CSVFormatter.format(metaMap), e);
            final long duration = System.currentTimeMillis() - startTimeMs;
            logStream.log(RECEIVE_LOG, metaMap, "ERROR", request.getRequestURI(), returnCode, -1, duration);
        }

        response.setStatus(returnCode);
    }
}
