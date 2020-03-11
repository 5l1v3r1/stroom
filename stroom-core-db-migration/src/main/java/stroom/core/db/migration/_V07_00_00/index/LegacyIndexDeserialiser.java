package stroom.core.db.migration._V07_00_00.index;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stroom.docref.DocRef;
import stroom.importexport.migration.LegacyXMLSerialiser;
import stroom.index.shared.AnalyzerType;
import stroom.index.shared.IndexDoc;
import stroom.index.shared.IndexField;
import stroom.index.shared.IndexFieldType;
import stroom.security.api.SecurityContext;
import stroom.util.xml.XMLMarshallerUtil;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class LegacyIndexDeserialiser {
    private static final Logger LOGGER = LoggerFactory.getLogger(LegacyIndexDeserialiser.class);

    public IndexDoc deserialise(final DocRef docRef, final Map<String, byte[]> dataMap, final long now, final String userId) {
        final _V07_00_00_Index oldIndex = new _V07_00_00_Index();
        final LegacyXMLSerialiser legacySerialiser = new LegacyXMLSerialiser();
        legacySerialiser.performImport(oldIndex, dataMap);

        IndexDoc document = new IndexDoc();
        document.setType(docRef.getType());
        document.setUuid(docRef.getUuid());
        document.setName(docRef.getName());
        document.setVersion(UUID.randomUUID().toString());
        if (oldIndex.getCreateTime() != null) {
            document.setCreateTime(oldIndex.getCreateTime());
        } else {
            document.setCreateTime(now);
        }
        if (oldIndex.getUpdateTime() != null) {
            document.setUpdateTime(oldIndex.getUpdateTime());
        } else {
            document.setUpdateTime(now);
        }
        if (oldIndex.getCreateUser() != null) {
            document.setCreateUser(oldIndex.getCreateUser());
        } else {
            document.setCreateUser(userId);
        }
        document.setUpdateUser(oldIndex.getUpdateUser());
        document.setDescription(oldIndex.getDescription());
        document.setMaxDocsPerShard(oldIndex.getMaxDocsPerShard());
        if (oldIndex.getPartitionBy() != null) {
            document.setPartitionBy(IndexDoc.PartitionBy.valueOf(oldIndex.getPartitionBy().name()));
        }
        document.setPartitionSize(oldIndex.getPartitionSize());
        document.setShardsPerPartition(oldIndex.getShardsPerPartition());
        document.setRetentionDayAge(oldIndex.getRetentionDayAge());

        final List<IndexField> indexFields = getIndexFieldsFromLegacyXML(oldIndex.getIndexFields());
        if (indexFields != null) {
            document.setFields(indexFields);
        }

        return document;
    }

    public List<IndexField> getIndexFieldsFromLegacyXML(final String xml) {
        if (xml != null) {
            try {
                final JAXBContext jaxbContext = JAXBContext.newInstance(_V07_00_00_IndexFields.class);
                final _V07_00_00_IndexFields oldIndexFields = XMLMarshallerUtil.unmarshal(jaxbContext, _V07_00_00_IndexFields.class, xml);
                List<_V07_00_00_IndexField> oldIndexFieldList = oldIndexFields.getIndexFields();
                if (oldIndexFieldList == null) {
                    oldIndexFieldList = Collections.emptyList();
                }

                return oldIndexFieldList
                        .stream()
                        .map(field -> new IndexField(
                                IndexFieldType.valueOf(field.getFieldType().name()),
                                field.getFieldName(),
                                AnalyzerType.valueOf(field.getAnalyzerType().name()),
                                field.isIndexed(),
                                field.isStored(),
                                field.isTermPositions(),
                                field.isCaseSensitive(),
                                field.getSupportedConditions()))
                        .collect(Collectors.toList());

            } catch (final JAXBException | RuntimeException e) {
                LOGGER.error("Unable to unmarshal index config", e);
            }
        }

        return null;
    }
}
