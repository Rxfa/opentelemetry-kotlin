package io.opentelemetry.kotlin.behavior

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class SpanProcessorBehaviorTest {

    @Test
    fun consoleStartsUnset() {
        assertNull(SpanProcessorBehavior().console)
    }

    @Test
    fun consoleStaysUnsetWhenNeitherLayerConfigured() {
        assertNull(SpanProcessorBehavior().mergeWith(SpanProcessorBehavior()).console)
    }

    @Test
    fun adoptsConsoleFromWhicheverLayerSuppliedIt() {
        val console = ConsoleExporterBehavior()

        assertEquals(
            console,
            SpanProcessorBehavior().mergeWith(SpanProcessorBehavior(console = console)).console,
        )
        assertEquals(
            console,
            SpanProcessorBehavior(console = console).mergeWith(SpanProcessorBehavior()).console,
        )
    }

    @Test
    fun httpStartsUnset() {
        assertNull(SpanProcessorBehavior().http)
    }

    @Test
    fun httpStaysUnsetWhenNeitherLayerConfigured() {
        assertNull(SpanProcessorBehavior().mergeWith(SpanProcessorBehavior()).http)
    }

    @Test
    fun adoptsHttpFromWhicheverLayerSuppliedIt() {
        val http = HttpExporterBehavior(
            endpoint = "https://example.com",
            tls = HttpTls(
                caFile = "caFile.pem",
                keyFile = "keyFile.pem",
                certFile = "certFile.pem",
            ),
            headers = mapOf("Content-Type" to "application/json"),
            headersList = listOf("Content-Type", "application/json"),
            compression = "gzip",
            maxRequestSize = 500,
            maxResponseSize = 500,
            timeout = 10_000,
            encoding = HttpEncoding.JSON
        )

        assertEquals(
            http,
            SpanProcessorBehavior().mergeWith(SpanProcessorBehavior(http = http)).http,
        )
        assertEquals(
            http,
            SpanProcessorBehavior(http = http).mergeWith(SpanProcessorBehavior()).http,
        )
    }
}
