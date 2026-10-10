package io.opentelemetry.kotlin.behavior

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class BatchSpanProcessorBehaviorTest {
    @Test
    fun everyFieldStartsUnset() {
        val processor = BatchSpanProcessorBehavior()
        assertNull(processor.scheduleDelay)
        assertNull(processor.exportTimeout)
        assertNull(processor.maxQueueSize)
        assertNull(processor.maxExportBatchSize)
        assertNull(processor.exporter)
    }

    @Test
    fun adoptsEverythingWhenLowerIsUnset() {
        val higher = BatchSpanProcessorBehavior(exporter = SpanExporterBehavior())
        assertEquals(higher, BatchSpanProcessorBehavior().mergeWith(higher))
    }

    @Test
    fun prefersHigherLayerForEveryField() {
        val lower = BatchSpanProcessorBehavior(
            exporter = SpanExporterBehavior(http = OtlpHttpSpanExporterBehavior(endpoint = "www.example1.com"))
        )
        val higher = BatchSpanProcessorBehavior(
            exporter = SpanExporterBehavior(http = OtlpHttpSpanExporterBehavior(endpoint = "www.example2.com"))
        )
        assertEquals(higher, lower.mergeWith(higher))
    }
}