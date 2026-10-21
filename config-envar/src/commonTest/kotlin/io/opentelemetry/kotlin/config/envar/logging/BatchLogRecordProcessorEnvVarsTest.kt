package io.opentelemetry.kotlin.config.envar.logging

import io.opentelemetry.kotlin.behavior.BatchLogRecordProcessorBehavior
import io.opentelemetry.kotlin.config.envar.reader.EnvVarReadWarning
import io.opentelemetry.kotlin.config.envar.reader.reportingEnvVarReader
import kotlin.test.Test
import kotlin.test.assertEquals

internal class BatchLogRecordProcessorEnvVarsTest {
    @Test
    fun readDefaultBatchSettings() {
        assertEquals(
            BatchLogRecordProcessorBehavior(
                scheduleDelay = BatchLogRecordProcessorBehavior.DEFAULT_SCHEDULE_DELAY,
                exportTimeout = BatchLogRecordProcessorBehavior.DEFAULT_EXPORT_TIMEOUT,
                maxQueueSize = BatchLogRecordProcessorBehavior.DEFAULT_MAX_QUEUE_SIZE,
                maxExportBatchSize = BatchLogRecordProcessorBehavior.DEFAULT_MAX_EXPORT_BATCH_SIZE,
            ),
            BatchLogRecordProcessorEnvVars(reportingEnvVarReader({ null })).toBehavior()
        )
    }

    @Test
    fun readBatchSettings() {
        val env = mapOf(
            BatchLogRecordProcessorEnvVars.SCHEDULE_DELAY to "100",
            BatchLogRecordProcessorEnvVars.EXPORT_TIMEOUT to "0",
            BatchLogRecordProcessorEnvVars.MAX_QUEUE_SIZE to "40",
            BatchLogRecordProcessorEnvVars.MAX_EXPORT_BATCH_SIZE to "20",
        )
        assertEquals(
            BatchLogRecordProcessorBehavior(
                scheduleDelay = 100,
                exportTimeout = 0,
                maxQueueSize = 40,
                maxExportBatchSize = 20
            ),
            BatchLogRecordProcessorEnvVars(reportingEnvVarReader(env::get)).toBehavior(),
        )
    }

    @Test
    fun ignoresInvalidSizes() {
        val warnings = mutableListOf<EnvVarReadWarning>()
        val reader = reportingEnvVarReader({ "0" }, warnings::add)
        assertEquals(
            BatchLogRecordProcessorBehavior(
                scheduleDelay = 0,
                exportTimeout = 0,
                maxQueueSize = BatchLogRecordProcessorBehavior.DEFAULT_MAX_QUEUE_SIZE,
                maxExportBatchSize = BatchLogRecordProcessorBehavior.DEFAULT_MAX_EXPORT_BATCH_SIZE,

            ),
            BatchLogRecordProcessorEnvVars(reader).toBehavior(),
        )
        assertEquals(2, warnings.size)
    }
}
