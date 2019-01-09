/*
 * This file is generated by jOOQ.
 */
package stroom.processor.impl.db;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;

import stroom.processor.impl.db.tables.Processor;
import stroom.processor.impl.db.tables.ProcessorFilter;
import stroom.processor.impl.db.tables.ProcessorFilterTask;
import stroom.processor.impl.db.tables.ProcessorFilterTracker;


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
public class Stroom extends SchemaImpl {

    private static final long serialVersionUID = 1810323242;

    /**
     * The reference instance of <code>stroom</code>
     */
    public static final Stroom STROOM = new Stroom();

    /**
     * The table <code>stroom.processor</code>.
     */
    public final Processor PROCESSOR = stroom.processor.impl.db.tables.Processor.PROCESSOR;

    /**
     * The table <code>stroom.processor_filter</code>.
     */
    public final ProcessorFilter PROCESSOR_FILTER = stroom.processor.impl.db.tables.ProcessorFilter.PROCESSOR_FILTER;

    /**
     * The table <code>stroom.processor_filter_task</code>.
     */
    public final ProcessorFilterTask PROCESSOR_FILTER_TASK = stroom.processor.impl.db.tables.ProcessorFilterTask.PROCESSOR_FILTER_TASK;

    /**
     * The table <code>stroom.processor_filter_tracker</code>.
     */
    public final ProcessorFilterTracker PROCESSOR_FILTER_TRACKER = stroom.processor.impl.db.tables.ProcessorFilterTracker.PROCESSOR_FILTER_TRACKER;

    /**
     * No further instances allowed
     */
    private Stroom() {
        super("stroom", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            Processor.PROCESSOR,
            ProcessorFilter.PROCESSOR_FILTER,
            ProcessorFilterTask.PROCESSOR_FILTER_TASK,
            ProcessorFilterTracker.PROCESSOR_FILTER_TRACKER);
    }
}
