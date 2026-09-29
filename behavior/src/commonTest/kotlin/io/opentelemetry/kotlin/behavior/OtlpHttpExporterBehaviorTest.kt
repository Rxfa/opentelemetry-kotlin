package io.opentelemetry.kotlin.behavior

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class OtlpHttpExporterBehaviorTest {
    @Test
    fun everyFieldStartsUnset() {
        val httpExporter = OtlpHttpExporterBehavior()
        assertNull(httpExporter.endpoint)
        assertNull(httpExporter.timeout)
        assertNull(httpExporter.headers)
    }

    @Test
    fun adoptsEverythingWhenLowerIsUnset() {
        val higher = OtlpHttpExporterBehavior(
            endpoint = "https://example.com",
            timeout = 10_000,
            headers = mapOf("a" to "b"),
        )
        assertEquals(higher, OtlpHttpExporterBehavior().mergeWith(higher))
    }

    @Test
    fun prefersHigherLayerForEveryField() {
        val lower = OtlpHttpExporterBehavior(
            endpoint = "https://example.com",
            timeout = 10_000,
            headers = mapOf("a" to "b"),
        )
        val higher = OtlpHttpExporterBehavior(
            endpoint = "https://example.com/2",
            timeout = 20_000,
            headers = mapOf("c" to "d"),
        )
        assertEquals(higher, lower.mergeWith(higher))
    }
}