package stroom.refdata.offheapstore;

import com.google.inject.assistedinject.Assisted;
import net.sf.saxon.event.PipelineConfiguration;
import net.sf.saxon.event.Receiver;
import net.sf.saxon.trans.XPathException;
import stroom.refdata.offheapstore.serdes.RefDataValueSerde;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;


public class RefDataValueProxyConsumer {

    private final Receiver receiver;
    private final PipelineConfiguration pipelineConfiguration;

    // injected map of typeId to the appropriate bytebuffer consumer factory
    private final Map<Integer, AbstractByteBufferConsumer.Factory> typeToByteBufferConsumerFactoryMap;
    // map to hold onto the
    private final Map<Integer, RefDataValueByteBufferConsumer> typeToConsumerMap = new HashMap<>();

    @Inject
    public RefDataValueProxyConsumer(
            @Assisted final Receiver receiver,
            @Assisted final PipelineConfiguration pipelineConfiguration,
            final Map<Integer, AbstractByteBufferConsumer.Factory> typeToByteBufferConsumerFactoryMap) {
        this.receiver = receiver;
        this.pipelineConfiguration = pipelineConfiguration;
        this.typeToByteBufferConsumerFactoryMap = typeToByteBufferConsumerFactoryMap;

    }

    public void startDocument() throws XPathException {
        receiver.setPipelineConfiguration(pipelineConfiguration);
        receiver.open();
        receiver.startDocument(0);
    }

    public void endDocument() throws XPathException {
        receiver.endDocument();
        receiver.close();
    }

    public void consume(final RefDataValueProxy refDataValueProxy) throws XPathException {

        //
        refDataValueProxy.consumeBytes(byteBuffer -> {

            // find out what type of value we are dealing with
            final Integer typeId = RefDataValueSerde.getTypeId(byteBuffer);

            // work out which byteBufferConsumer to use based on the typeId in the value byteBuffer
            final RefDataValueByteBufferConsumer consumer = typeToConsumerMap.computeIfAbsent(
                    typeId, typeIdKey ->
                            typeToByteBufferConsumerFactoryMap.get(typeIdKey)
                                    .create(receiver, pipelineConfiguration)
            );

            // now we have the appropriate consumer for the value type, consume the value
            consumer.consumeBytes(receiver, byteBuffer);
        });
    }

    public interface Factory {
        RefDataValueProxyConsumer create(final Receiver receiver,
                                         final PipelineConfiguration pipelineConfiguration);
    }

}
