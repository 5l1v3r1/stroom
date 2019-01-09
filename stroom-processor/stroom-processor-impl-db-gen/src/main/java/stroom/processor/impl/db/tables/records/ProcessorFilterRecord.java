/*
 * This file is generated by jOOQ.
 */
package stroom.processor.impl.db.tables.records;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record11;
import org.jooq.Row11;
import org.jooq.impl.UpdatableRecordImpl;

import stroom.processor.impl.db.tables.ProcessorFilter;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProcessorFilterRecord extends UpdatableRecordImpl<ProcessorFilterRecord> implements Record11<Integer, Byte, Long, String, Long, String, String, Integer, Integer, Integer, Boolean> {

    private static final long serialVersionUID = 1235225965;

    /**
     * Setter for <code>stroom.processor_filter.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>stroom.processor_filter.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>stroom.processor_filter.version</code>.
     */
    public void setVersion(Byte value) {
        set(1, value);
    }

    /**
     * Getter for <code>stroom.processor_filter.version</code>.
     */
    public Byte getVersion() {
        return (Byte) get(1);
    }

    /**
     * Setter for <code>stroom.processor_filter.create_time_ms</code>.
     */
    public void setCreateTimeMs(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>stroom.processor_filter.create_time_ms</code>.
     */
    public Long getCreateTimeMs() {
        return (Long) get(2);
    }

    /**
     * Setter for <code>stroom.processor_filter.create_user</code>.
     */
    public void setCreateUser(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>stroom.processor_filter.create_user</code>.
     */
    public String getCreateUser() {
        return (String) get(3);
    }

    /**
     * Setter for <code>stroom.processor_filter.update_time_ms</code>.
     */
    public void setUpdateTimeMs(Long value) {
        set(4, value);
    }

    /**
     * Getter for <code>stroom.processor_filter.update_time_ms</code>.
     */
    public Long getUpdateTimeMs() {
        return (Long) get(4);
    }

    /**
     * Setter for <code>stroom.processor_filter.update_user</code>.
     */
    public void setUpdateUser(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>stroom.processor_filter.update_user</code>.
     */
    public String getUpdateUser() {
        return (String) get(5);
    }

    /**
     * Setter for <code>stroom.processor_filter.data</code>.
     */
    public void setData(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>stroom.processor_filter.data</code>.
     */
    public String getData() {
        return (String) get(6);
    }

    /**
     * Setter for <code>stroom.processor_filter.priority</code>.
     */
    public void setPriority(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>stroom.processor_filter.priority</code>.
     */
    public Integer getPriority() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>stroom.processor_filter.fk_processor_id</code>.
     */
    public void setFkProcessorId(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>stroom.processor_filter.fk_processor_id</code>.
     */
    public Integer getFkProcessorId() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>stroom.processor_filter.fk_processor_filter_tracker_id</code>.
     */
    public void setFkProcessorFilterTrackerId(Integer value) {
        set(9, value);
    }

    /**
     * Getter for <code>stroom.processor_filter.fk_processor_filter_tracker_id</code>.
     */
    public Integer getFkProcessorFilterTrackerId() {
        return (Integer) get(9);
    }

    /**
     * Setter for <code>stroom.processor_filter.enabled</code>.
     */
    public void setEnabled(Boolean value) {
        set(10, value);
    }

    /**
     * Getter for <code>stroom.processor_filter.enabled</code>.
     */
    public Boolean getEnabled() {
        return (Boolean) get(10);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record11 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row11<Integer, Byte, Long, String, Long, String, String, Integer, Integer, Integer, Boolean> fieldsRow() {
        return (Row11) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row11<Integer, Byte, Long, String, Long, String, String, Integer, Integer, Integer, Boolean> valuesRow() {
        return (Row11) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return ProcessorFilter.PROCESSOR_FILTER.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field2() {
        return ProcessorFilter.PROCESSOR_FILTER.VERSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field3() {
        return ProcessorFilter.PROCESSOR_FILTER.CREATE_TIME_MS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return ProcessorFilter.PROCESSOR_FILTER.CREATE_USER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field5() {
        return ProcessorFilter.PROCESSOR_FILTER.UPDATE_TIME_MS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return ProcessorFilter.PROCESSOR_FILTER.UPDATE_USER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return ProcessorFilter.PROCESSOR_FILTER.DATA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return ProcessorFilter.PROCESSOR_FILTER.PRIORITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return ProcessorFilter.PROCESSOR_FILTER.FK_PROCESSOR_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field10() {
        return ProcessorFilter.PROCESSOR_FILTER.FK_PROCESSOR_FILTER_TRACKER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field11() {
        return ProcessorFilter.PROCESSOR_FILTER.ENABLED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component2() {
        return getVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component3() {
        return getCreateTimeMs();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getCreateUser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component5() {
        return getUpdateTimeMs();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getUpdateUser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component8() {
        return getPriority();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component9() {
        return getFkProcessorId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component10() {
        return getFkProcessorFilterTrackerId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean component11() {
        return getEnabled();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value2() {
        return getVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value3() {
        return getCreateTimeMs();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getCreateUser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value5() {
        return getUpdateTimeMs();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getUpdateUser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getPriority();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value9() {
        return getFkProcessorId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value10() {
        return getFkProcessorFilterTrackerId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value11() {
        return getEnabled();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessorFilterRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessorFilterRecord value2(Byte value) {
        setVersion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessorFilterRecord value3(Long value) {
        setCreateTimeMs(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessorFilterRecord value4(String value) {
        setCreateUser(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessorFilterRecord value5(Long value) {
        setUpdateTimeMs(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessorFilterRecord value6(String value) {
        setUpdateUser(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessorFilterRecord value7(String value) {
        setData(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessorFilterRecord value8(Integer value) {
        setPriority(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessorFilterRecord value9(Integer value) {
        setFkProcessorId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessorFilterRecord value10(Integer value) {
        setFkProcessorFilterTrackerId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessorFilterRecord value11(Boolean value) {
        setEnabled(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessorFilterRecord values(Integer value1, Byte value2, Long value3, String value4, Long value5, String value6, String value7, Integer value8, Integer value9, Integer value10, Boolean value11) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ProcessorFilterRecord
     */
    public ProcessorFilterRecord() {
        super(ProcessorFilter.PROCESSOR_FILTER);
    }

    /**
     * Create a detached, initialised ProcessorFilterRecord
     */
    public ProcessorFilterRecord(Integer id, Byte version, Long createTimeMs, String createUser, Long updateTimeMs, String updateUser, String data, Integer priority, Integer fkProcessorId, Integer fkProcessorFilterTrackerId, Boolean enabled) {
        super(ProcessorFilter.PROCESSOR_FILTER);

        set(0, id);
        set(1, version);
        set(2, createTimeMs);
        set(3, createUser);
        set(4, updateTimeMs);
        set(5, updateUser);
        set(6, data);
        set(7, priority);
        set(8, fkProcessorId);
        set(9, fkProcessorFilterTrackerId);
        set(10, enabled);
    }
}
