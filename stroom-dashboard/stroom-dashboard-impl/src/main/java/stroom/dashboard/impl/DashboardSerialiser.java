package stroom.dashboard.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stroom.core.db.migration._V07_00_00.dashboard.LegacyDashboardDeserialiser;
import stroom.dashboard.shared.DashboardConfig;
import stroom.dashboard.shared.DashboardDoc;
import stroom.docstore.api.DocumentSerialiser2;
import stroom.docstore.api.Serialiser2;
import stroom.docstore.api.Serialiser2Factory;
import stroom.util.string.EncodingUtil;

import javax.inject.Inject;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class DashboardSerialiser implements DocumentSerialiser2<DashboardDoc> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardSerialiser.class);

    private static final String XML = "xml";
    private static final String JSON = "json";

    private final Serialiser2<DashboardDoc> delegate;
    private final Serialiser2<DashboardConfig> dashboardConfigSerialiser;
    private final LegacyDashboardDeserialiser legacyDashboardDeserialiser;

    @Inject
    public DashboardSerialiser(final Serialiser2Factory serialiser2Factory,
                               final LegacyDashboardDeserialiser legacyDashboardDeserialiser) {
        this.delegate = serialiser2Factory.createSerialiser(DashboardDoc.class);
        this.dashboardConfigSerialiser = serialiser2Factory.createSerialiser(DashboardConfig.class);
        this.legacyDashboardDeserialiser = legacyDashboardDeserialiser;
    }

    @Override
    public DashboardDoc read(final Map<String, byte[]> data) throws IOException {
        final DashboardDoc document = delegate.read(data);

        // Deal with old XML format data.
        final String xml = EncodingUtil.asString(data.get(XML));
        if (xml != null) {
            document.setDashboardConfig(legacyDashboardDeserialiser.getDashboardConfigFromLegacyXML(xml));
        }

        final byte[] jsonData = data.get(JSON);
        if (jsonData != null) {
            try {
                final DashboardConfig dashboardConfig = dashboardConfigSerialiser.read(jsonData);
                document.setDashboardConfig(dashboardConfig);
            } catch (final RuntimeException e) {
                LOGGER.error("Unable to unmarshal dashboard config", e);
            }
        }

        return document;
    }

    @Override
    public Map<String, byte[]> write(final DashboardDoc document) throws IOException {
        final DashboardConfig dashboardConfig = document.getDashboardConfig();
        document.setDashboardConfig(null);

        final Map<String, byte[]> data = delegate.write(document);

        if (dashboardConfig != null) {
            final StringWriter stringWriter = new StringWriter();
            dashboardConfigSerialiser.write(stringWriter, dashboardConfig);
            data.put(JSON, EncodingUtil.asBytes(stringWriter.toString()));
            document.setDashboardConfig(dashboardConfig);
        }

        return data;
    }
}