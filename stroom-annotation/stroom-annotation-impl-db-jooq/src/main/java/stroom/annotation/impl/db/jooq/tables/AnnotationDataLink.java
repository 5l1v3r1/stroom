/*
 * This file is generated by jOOQ.
 */
package stroom.annotation.impl.db.jooq.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import stroom.annotation.impl.db.jooq.Indexes;
import stroom.annotation.impl.db.jooq.Keys;
import stroom.annotation.impl.db.jooq.Stroom;
import stroom.annotation.impl.db.jooq.tables.records.AnnotationDataLinkRecord;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AnnotationDataLink extends TableImpl<AnnotationDataLinkRecord> {

    private static final long serialVersionUID = 1581909143;

    /**
     * The reference instance of <code>stroom.annotation_data_link</code>
     */
    public static final AnnotationDataLink ANNOTATION_DATA_LINK = new AnnotationDataLink();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AnnotationDataLinkRecord> getRecordType() {
        return AnnotationDataLinkRecord.class;
    }

    /**
     * The column <code>stroom.annotation_data_link.id</code>.
     */
    public final TableField<AnnotationDataLinkRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>stroom.annotation_data_link.fk_annotation_id</code>.
     */
    public final TableField<AnnotationDataLinkRecord, Long> FK_ANNOTATION_ID = createField("fk_annotation_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>stroom.annotation_data_link.stream_id</code>.
     */
    public final TableField<AnnotationDataLinkRecord, Long> STREAM_ID = createField("stream_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>stroom.annotation_data_link.event_id</code>.
     */
    public final TableField<AnnotationDataLinkRecord, Long> EVENT_ID = createField("event_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * Create a <code>stroom.annotation_data_link</code> table reference
     */
    public AnnotationDataLink() {
        this(DSL.name("annotation_data_link"), null);
    }

    /**
     * Create an aliased <code>stroom.annotation_data_link</code> table reference
     */
    public AnnotationDataLink(String alias) {
        this(DSL.name(alias), ANNOTATION_DATA_LINK);
    }

    /**
     * Create an aliased <code>stroom.annotation_data_link</code> table reference
     */
    public AnnotationDataLink(Name alias) {
        this(alias, ANNOTATION_DATA_LINK);
    }

    private AnnotationDataLink(Name alias, Table<AnnotationDataLinkRecord> aliased) {
        this(alias, aliased, null);
    }

    private AnnotationDataLink(Name alias, Table<AnnotationDataLinkRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> AnnotationDataLink(Table<O> child, ForeignKey<O, AnnotationDataLinkRecord> key) {
        super(child, key, ANNOTATION_DATA_LINK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Stroom.STROOM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.ANNOTATION_DATA_LINK_FK_ANNOTATION_ID_STREAM_ID_EVENT_ID, Indexes.ANNOTATION_DATA_LINK_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<AnnotationDataLinkRecord, Long> getIdentity() {
        return Keys.IDENTITY_ANNOTATION_DATA_LINK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<AnnotationDataLinkRecord> getPrimaryKey() {
        return Keys.KEY_ANNOTATION_DATA_LINK_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<AnnotationDataLinkRecord>> getKeys() {
        return Arrays.<UniqueKey<AnnotationDataLinkRecord>>asList(Keys.KEY_ANNOTATION_DATA_LINK_PRIMARY, Keys.KEY_ANNOTATION_DATA_LINK_FK_ANNOTATION_ID_STREAM_ID_EVENT_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<AnnotationDataLinkRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<AnnotationDataLinkRecord, ?>>asList(Keys.ANNOTATION_DATA_LINK_FK_ANNOTATION_ID);
    }

    public Annotation annotation() {
        return new Annotation(this, Keys.ANNOTATION_DATA_LINK_FK_ANNOTATION_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnnotationDataLink as(String alias) {
        return new AnnotationDataLink(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnnotationDataLink as(Name alias) {
        return new AnnotationDataLink(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public AnnotationDataLink rename(String name) {
        return new AnnotationDataLink(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public AnnotationDataLink rename(Name name) {
        return new AnnotationDataLink(name, null);
    }
}
