/*
 * Copyright 2016 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package stroom.statistics.impl.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stroom.cluster.lock.api.ClusterLockService;
import stroom.task.api.TaskContext;
import stroom.util.logging.LogExecutionTime;

import javax.inject.Inject;
import java.sql.SQLException;
import java.time.Instant;
import java.util.concurrent.locks.ReentrantLock;

class SQLStatisticAggregationManager {
    /**
     * The number of records to add to the aggregate from the aggregate source
     * table on each pass
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SQLStatisticAggregationManager.class);
    /**
     * The cluster lock to acquire to prevent other nodes from concurrently
     * aggregating statistics.
     */
    private static final String LOCK_NAME = "SQLStatisticAggregationManager";
    private static final ReentrantLock guard = new ReentrantLock();
    private final ClusterLockService clusterLockService;
    private final SQLStatisticAggregationTransactionHelper helper;
    private final TaskContext taskContext;
    private int batchSize;

    @Inject
    SQLStatisticAggregationManager(final ClusterLockService clusterLockService,
                                   final SQLStatisticAggregationTransactionHelper helper,
                                   final TaskContext taskContext,
                                   final SQLStatisticsConfig sqlStatisticsConfig) {
        this.clusterLockService = clusterLockService;
        this.helper = helper;
        this.taskContext = taskContext;
        this.batchSize = sqlStatisticsConfig.getStatisticAggregationBatchSize();
    }

    void aggregate() {
        LOGGER.info("SQL Statistic Aggregation - start");
        clusterLockService.tryLock(LOCK_NAME, () -> {
            try {
                final LogExecutionTime logExecutionTime = new LogExecutionTime();
                aggregate(Instant.now());
                LOGGER.info("SQL Statistic Aggregation - finished in {}", logExecutionTime);
            } catch (final RuntimeException e) {
                LOGGER.error(e.getMessage(), e);
            }
        });
    }

    /**
     * Step 1 - Move source values into value table with precision 1<br/>
     * Step 2 - Reduce precisions possibly creating duplicates in same table
     * <br/>
     * Step 3 - Remove duplicates using temporary table<br/>
     */
    void aggregate(final Instant timeNow) {
        guard.lock();
        try {
            LOGGER.debug("aggregate() Called for SQL stats - Start timeNow = {}", timeNow);
            final LogExecutionTime logExecutionTime = new LogExecutionTime();

            // TODO delete any rows in SQL_STAT_VAL that have a data older
            // than
            // (now minus maxProcessingAge) rounded
            // to the most coarse precision. Needs to be done first so we
            // don't
            // have to aggregate any old data
            try {
                helper.deleteOldStats(timeNow, taskContext);

                long processedCount;
                int iteration = 0;
                // Process batches of records until we have processed one
                // that
                // was not a full batch
                do {
                    processedCount = helper.aggregateConfigStage1(taskContext, "Iteration: " + ++iteration + "",
                            batchSize, timeNow);

                } while (processedCount == batchSize && !Thread.currentThread().isInterrupted());

                if (!Thread.currentThread().isInterrupted()) {
                    helper.aggregateConfigStage2(taskContext, "Final Reduce", timeNow);
                }

            } catch (final SQLException ex) {
                throw new RuntimeException(ex.getMessage(), ex);
            } finally {
                LOGGER.debug("aggregate() - Finished for SQL stats in {} timeNowOverride = {}",
                    logExecutionTime, timeNow);
            }
        } finally {
            guard.unlock();
        }
    }

    void setBatchSize(final int batchSize) {
        this.batchSize = batchSize;
    }
}
