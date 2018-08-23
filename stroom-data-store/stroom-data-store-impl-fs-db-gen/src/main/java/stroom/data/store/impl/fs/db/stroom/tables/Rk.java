/*
 * This file is generated by jOOQ.
*/
package stroom.data.store.impl.fs.db.stroom.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import stroom.data.store.impl.fs.db.stroom.Indexes;
import stroom.data.store.impl.fs.db.stroom.Keys;
import stroom.data.store.impl.fs.db.stroom.Stroom;
import stroom.data.store.impl.fs.db.stroom.tables.records.RkRecord;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Rk extends TableImpl<RkRecord> {

    private static final long serialVersionUID = 1655930452;

    /**
     * The reference instance of <code>stroom.RK</code>
     */
    public static final Rk RK = new Rk();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<RkRecord> getRecordType() {
        return RkRecord.class;
    }

    /**
     * The column <code>stroom.RK.ID</code>.
     */
    public final TableField<RkRecord, Integer> ID = createField("ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>stroom.RK.VER</code>.
     */
    public final TableField<RkRecord, Byte> VER = createField("VER", org.jooq.impl.SQLDataType.TINYINT.nullable(false), this, "");

    /**
     * The column <code>stroom.RK.CRT_USER</code>.
     */
    public final TableField<RkRecord, String> CRT_USER = createField("CRT_USER", org.jooq.impl.SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>stroom.RK.UPD_USER</code>.
     */
    public final TableField<RkRecord, String> UPD_USER = createField("UPD_USER", org.jooq.impl.SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>stroom.RK.NAME</code>.
     */
    public final TableField<RkRecord, String> NAME = createField("NAME", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>stroom.RK.CRT_MS</code>.
     */
    public final TableField<RkRecord, Long> CRT_MS = createField("CRT_MS", org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>stroom.RK.UPD_MS</code>.
     */
    public final TableField<RkRecord, Long> UPD_MS = createField("UPD_MS", org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * Create a <code>stroom.RK</code> table reference
     */
    public Rk() {
        this(DSL.name("RK"), null);
    }

    /**
     * Create an aliased <code>stroom.RK</code> table reference
     */
    public Rk(String alias) {
        this(DSL.name(alias), RK);
    }

    /**
     * Create an aliased <code>stroom.RK</code> table reference
     */
    public Rk(Name alias) {
        this(alias, RK);
    }

    private Rk(Name alias, Table<RkRecord> aliased) {
        this(alias, aliased, null);
    }

    private Rk(Name alias, Table<RkRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
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
        return Arrays.<Index>asList(Indexes.RK_NAME, Indexes.RK_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<RkRecord, Integer> getIdentity() {
        return Keys.IDENTITY_RK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<RkRecord> getPrimaryKey() {
        return Keys.KEY_RK_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<RkRecord>> getKeys() {
        return Arrays.<UniqueKey<RkRecord>>asList(Keys.KEY_RK_PRIMARY, Keys.KEY_RK_NAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Rk as(String alias) {
        return new Rk(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Rk as(Name alias) {
        return new Rk(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Rk rename(String name) {
        return new Rk(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Rk rename(Name name) {
        return new Rk(name, null);
    }
}
