/*
 * This file is generated by jOOQ.
 */
package stroom.storedquery.impl.db.jooq.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row11;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import stroom.storedquery.impl.db.jooq.Indexes;
import stroom.storedquery.impl.db.jooq.Keys;
import stroom.storedquery.impl.db.jooq.Stroom;
import stroom.storedquery.impl.db.jooq.tables.records.QueryRecord;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Query extends TableImpl<QueryRecord> {

    private static final long serialVersionUID = -69451907;

    /**
     * The reference instance of <code>stroom.query</code>
     */
    public static final Query QUERY = new Query();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<QueryRecord> getRecordType() {
        return QueryRecord.class;
    }

    /**
     * The column <code>stroom.query.id</code>.
     */
    public final TableField<QueryRecord, Integer> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>stroom.query.version</code>.
     */
    public final TableField<QueryRecord, Integer> VERSION = createField(DSL.name("version"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>stroom.query.create_time_ms</code>.
     */
    public final TableField<QueryRecord, Long> CREATE_TIME_MS = createField(DSL.name("create_time_ms"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>stroom.query.create_user</code>.
     */
    public final TableField<QueryRecord, String> CREATE_USER = createField(DSL.name("create_user"), org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>stroom.query.update_time_ms</code>.
     */
    public final TableField<QueryRecord, Long> UPDATE_TIME_MS = createField(DSL.name("update_time_ms"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>stroom.query.update_user</code>.
     */
    public final TableField<QueryRecord, String> UPDATE_USER = createField(DSL.name("update_user"), org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>stroom.query.dashboard_uuid</code>.
     */
    public final TableField<QueryRecord, String> DASHBOARD_UUID = createField(DSL.name("dashboard_uuid"), org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>stroom.query.component_id</code>.
     */
    public final TableField<QueryRecord, String> COMPONENT_ID = createField(DSL.name("component_id"), org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>stroom.query.name</code>.
     */
    public final TableField<QueryRecord, String> NAME = createField(DSL.name("name"), org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>stroom.query.data</code>.
     */
    public final TableField<QueryRecord, String> DATA = createField(DSL.name("data"), org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>stroom.query.favourite</code>.
     */
    public final TableField<QueryRecord, Boolean> FAVOURITE = createField(DSL.name("favourite"), org.jooq.impl.SQLDataType.BIT.nullable(false), this, "");

    /**
     * Create a <code>stroom.query</code> table reference
     */
    public Query() {
        this(DSL.name("query"), null);
    }

    /**
     * Create an aliased <code>stroom.query</code> table reference
     */
    public Query(String alias) {
        this(DSL.name(alias), QUERY);
    }

    /**
     * Create an aliased <code>stroom.query</code> table reference
     */
    public Query(Name alias) {
        this(alias, QUERY);
    }

    private Query(Name alias, Table<QueryRecord> aliased) {
        this(alias, aliased, null);
    }

    private Query(Name alias, Table<QueryRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Query(Table<O> child, ForeignKey<O, QueryRecord> key) {
        super(child, key, QUERY);
    }

    @Override
    public Schema getSchema() {
        return Stroom.STROOM;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.QUERY_PRIMARY);
    }

    @Override
    public Identity<QueryRecord, Integer> getIdentity() {
        return Keys.IDENTITY_QUERY;
    }

    @Override
    public UniqueKey<QueryRecord> getPrimaryKey() {
        return Keys.KEY_QUERY_PRIMARY;
    }

    @Override
    public List<UniqueKey<QueryRecord>> getKeys() {
        return Arrays.<UniqueKey<QueryRecord>>asList(Keys.KEY_QUERY_PRIMARY);
    }

    @Override
    public TableField<QueryRecord, Integer> getRecordVersion() {
        return VERSION;
    }

    @Override
    public Query as(String alias) {
        return new Query(DSL.name(alias), this);
    }

    @Override
    public Query as(Name alias) {
        return new Query(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Query rename(String name) {
        return new Query(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Query rename(Name name) {
        return new Query(name, null);
    }

    // -------------------------------------------------------------------------
    // Row11 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row11<Integer, Integer, Long, String, Long, String, String, String, String, String, Boolean> fieldsRow() {
        return (Row11) super.fieldsRow();
    }
}
