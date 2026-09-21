package io.opentelemetry.kotlin.behavior

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class LogRecordProcessorBehaviorTest {

    @Test
    fun consoleStartsUnset() {
        assertNull(LogRecordProcessorBehavior().console)
    }

    @Test
    fun staysUnsetWhenNeitherLayerConfiguredConsole() {
        assertNull(LogRecordProcessorBehavior().mergeWith(LogRecordProcessorBehavior()).console)
    }

    @Test
    fun adoptsConsoleFromWhicheverLayerSuppliedIt() {
        val console = ConsoleExporterBehavior()

        assertEquals(
            console,
            LogRecordProcessorBehavior().mergeWith(LogRecordProcessorBehavior(console = console)).console,
        )
        assertEquals(
            console,
            LogRecordProcessorBehavior(console = console).mergeWith(LogRecordProcessorBehavior()).console,
        )
    }

    @Test
    fun httpStartsUnset() {
        assertNull(LogRecordProcessorBehavior().http)
    }

    @Test
    fun httpStaysUnsetWhenNeitherLayerConfigured() {
        assertNull(LogRecordProcessorBehavior().mergeWith(LogRecordProcessorBehavior()).http)
    }

    @Test
    fun adoptsHttpFromWhicheverLayerSuppliedIt() {
        val http = OtlpHttpExporterBehavior(
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
            LogRecordProcessorBehavior().mergeWith(LogRecordProcessorBehavior(http = http)).http,
        )
        assertEquals(
            http,
            LogRecordProcessorBehavior(http = http).mergeWith(LogRecordProcessorBehavior()).http,
        )
    }
}
