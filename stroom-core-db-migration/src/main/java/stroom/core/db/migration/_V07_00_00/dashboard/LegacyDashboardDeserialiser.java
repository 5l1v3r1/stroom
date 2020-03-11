package stroom.core.db.migration._V07_00_00.dashboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stroom.dashboard.shared.DashboardConfig;
import stroom.dashboard.shared.DashboardDoc;
import stroom.docref.DocRef;

import java.util.Map;

public class LegacyDashboardDeserialiser {
    private static final Logger LOGGER = LoggerFactory.getLogger(LegacyDashboardDeserialiser.class);

    public DashboardDoc deserialise(final DocRef docRef, final Map<String, byte[]> dataMap) {

//        final OldDashboard oldDashboard = new OldDashboard();
//        final LegacyXMLSerialiser legacySerialiser = new LegacyXMLSerialiser();
//        legacySerialiser.performImport(oldDashboard, dataMap);
//
//        final long now = System.currentTimeMillis();
//        final String userId = securityContext.getUserId();
//
//        document = new DashboardDoc();
//        document.setType(docRef.getType());
//        document.setUuid(uuid);
//        document.setName(docRef.getName());
//        document.setVersion(UUID.randomUUID().toString());
//        document.setCreateTime(now);
//        document.setUpdateTime(now);
//        document.setCreateUser(userId);
//        document.setUpdateUser(userId);


//        final _V07_00_00_Index oldIndex = new _V07_00_00_Index();
//        final LegacyXMLSerialiser legacySerialiser = new LegacyXMLSerialiser();
//        legacySerialiser.performImport(oldIndex, dataMap);
//
//        final long now = System.currentTimeMillis();
//        IndexDoc document = new IndexDoc();
//        document.setType(docRef.getType());
//        document.setUuid(docRef.getUuid());
//        document.setName(docRef.getName());
//        document.setVersion(UUID.randomUUID().toString());
//        if (oldIndex.getCreateTime() != null) {
//            document.setCreateTime(oldIndex.getCreateTime());
//        } else {
//            document.setCreateTime(now);
//        }
//        if (oldIndex.getUpdateTime() != null) {
//            document.setUpdateTime(oldIndex.getUpdateTime());
//        } else {
//            document.setUpdateTime(now);
//        }
//        if (oldIndex.getCreateUser() != null) {
//            document.setCreateUser(oldIndex.getCreateUser());
//        } else if (securityContext != null) {
//            document.setCreateUser(securityContext.getUserId());
//        }
//        document.setUpdateUser(oldIndex.getUpdateUser());
//        document.setDescription(oldIndex.getDescription());
//        document.setMaxDocsPerShard(oldIndex.getMaxDocsPerShard());
//        if (oldIndex.getPartitionBy() != null) {
//            document.setPartitionBy(IndexDoc.PartitionBy.valueOf(oldIndex.getPartitionBy().name()));
//        }
//        document.setPartitionSize(oldIndex.getPartitionSize());
//        document.setShardsPerPartition(oldIndex.getShardsPerPartition());
//        document.setRetentionDayAge(oldIndex.getRetentionDayAge());
//
//        final List<IndexField> indexFields = getIndexFieldsFromLegacyXML(oldIndex.getIndexFields());
//        if (indexFields != null) {
//            document.setFields(indexFields);
//        }
//
//        return document;

        return null;
    }

    public DashboardConfig getDashboardConfigFromLegacyXML(final String xml) {
        return null;
    }


//    public DashboardConfig getDashboardConfigFromLegacyXML(final String xml) {
//        if (xml != null) {
//            try {
//                final JAXBContext jaxbContext = JAXBContext.newInstance(DashboardConfig.class);
//                return XMLMarshallerUtil.unmarshal(jaxbContext, DashboardConfig.class, xml);
//            } catch (final JAXBException | RuntimeException e) {
//                LOGGER.error("Unable to unmarshal dashboard config", e);
//            }
//        }
//
//        return null;
//    }


//    private static final Logger LOGGER = LoggerFactory.getLogger(_V07_00_00_DashboardSerialiser.class);
//
//    private static final String XML = "xml";
//    private static final String JSON = "json";
//
//    private final ObjectMapper mapper;
//
//    public _V07_00_00_DashboardSerialiser() {
//        super(_V07_00_00_DashboardDoc.class);
//        mapper = getMapper(true);
//    }
//
//    @Override
//    public _V07_00_00_DashboardDoc read(final Map<String, byte[]> data) throws IOException {
//        final _V07_00_00_DashboardDoc document = super.read(data);
//
//        // Deal with old XML format data.
//        final String xml = _V07_00_00_EncodingUtil.asString(data.get(XML));
//        if (xml != null) {
//            document.setDashboardConfig(getDashboardConfigFromLegacyXML(xml));
//        }
//
//        final String json = _V07_00_00_EncodingUtil.asString(data.get(JSON));
//        if (json != null) {
//            try {
//                final _V07_00_00_DashboardConfig dashboardConfig = mapper.readValue(new StringReader(json), _V07_00_00_DashboardConfig.class);
//                document.setDashboardConfig(dashboardConfig);
//            } catch (final RuntimeException e) {
//                LOGGER.error("Unable to unmarshal dashboard config", e);
//            }
//        }
//
//        return document;
//    }
//
//    @Override
//    public Map<String, byte[]> write(final _V07_00_00_DashboardDoc document) throws IOException {
//        final Map<String, byte[]> data = super.write(document);
//
//        _V07_00_00_DashboardConfig dashboardConfig = document.getDashboardConfig();
//        if (dashboardConfig != null) {
//            final StringWriter stringWriter = new StringWriter();
//            mapper.writeValue(stringWriter, dashboardConfig);
//            data.put(JSON, _V07_00_00_EncodingUtil.asBytes(stringWriter.toString()));
//        }
//        return data;
//    }
//
//    public _V07_00_00_DashboardConfig getDashboardConfigFromLegacyXML(final String xml) {
//        if (xml != null) {
//            try {
//                final JAXBContext jaxbContext = JAXBContext.newInstance(_V07_00_00_DashboardConfig.class);
//                return _V07_00_00_XMLMarshallerUtil.unmarshal(jaxbContext, _V07_00_00_DashboardConfig.class, xml);
//            } catch (final JAXBException | RuntimeException e) {
//                LOGGER.error("Unable to unmarshal dashboard config", e);
//            }
//        }
//
//        return null;
//    }
}
