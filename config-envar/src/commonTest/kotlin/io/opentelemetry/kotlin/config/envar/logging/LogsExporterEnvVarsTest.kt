package io.opentelemetry.kotlin.config.envar.logging

import io.opentelemetry.kotlin.behavior.ConsoleExporterBehavior
import io.opentelemetry.kotlin.behavior.LogRecordProcessorBehavior
import io.opentelemetry.kotlin.behavior.OtlpHttpExporterBehavior
import io.opentelemetry.kotlin.config.envar.Exporter
import io.opentelemetry.kotlin.config.envar.OpenTelemetryEnvVars
import io.opentelemetry.kotlin.config.envar.reader.EnvVarReadWarning
import io.opentelemetry.kotlin.config.envar.reader.reportingEnvVarReader
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class LogsExporterEnvVarsTest {
    private val unknownExporter = "not_an_exporter"

    @Test
    fun `should leave unset env vars unset`() {
        assertNull(toBehavior { null })
    }

    @Test
    fun `should map implemented exporters`() {
        assertEquals(
            LogRecordProcessorBehavior(console = ConsoleExporterBehavior()),
            toBehavior(env(Exporter.CONSOLE.value)),
        )
        assertEquals(
            LogRecordProcessorBehavior(http = OtlpHttpExporterBehavior()),
            toBehavior(env(Exporter.OTLP.value)),
        )
    }

    @Test
    fun `should leave known non-implemented exporters unset`() {
        val exporters =
            listOf(Exporter.LOGGING, Exporter.NONE, Exporter.OTLP_STDOUT)
        exporters.forEach { exporter ->
            assertNull(
                toBehavior(env(exporter.value)),
                "<$exporter> should not configure a processor"
            )
        }
    }

    @Test
    fun `should leave unknown exporter unset`() {
        assertNull(toBehavior(env(unknownExporter)))
    }

    @Test
    fun `should warn on unknown exporter`() {
        val warnings = mutableListOf<EnvVarReadWarning>()
        LogsExporterEnvVars(reportingEnvVarReader(env(unknownExporter), warnings::add)).toBehavior()
        assertEquals(1, warnings.size)
        assertEquals(LogsExporterEnvVars.LOGS_EXPORTER, warnings.single().name)
    }

    @Test
    fun `should not warn when exporter is unset`() {
        val warnings = mutableListOf<EnvVarReadWarning>()
        LogsExporterEnvVars(reportingEnvVarReader({ null }, warnings::add)).toBehavior()
        assertEquals(emptyList(), warnings)
    }

    @Test
    fun `should not warn on known non-implemented exporters`() {
        val warnings = mutableListOf<EnvVarReadWarning>()
        LogsExporterEnvVars(reportingEnvVarReader(env(Exporter.LOGGING.value), warnings::add)).toBehavior()
        assertEquals(emptyList(), warnings)
    }

    @Test
    fun `Signal-specific configs override base configs`() {
        val configs = mutableMapOf(
            LogsExporterEnvVars.LOGS_EXPORTER to Exporter.OTLP.value,
            OpenTelemetryEnvVars.OTLP_ENDPOINT to "http://localhost:4317",
            OpenTelemetryEnvVars.OTLP_TIMEOUT to "1",
        )
        assertEquals(
            LogRecordProcessorBehavior(
                http = OtlpHttpExporterBehavior(
                    endpoint = "http://localhost:4317",
                    timeout = 1
                )
            ),
            toBehavior(configs::get),
        )
        configs.putAll(
            mapOf(
                LogsExporterEnvVars.OTLP_LOGS_ENDPOINT to "http://localhost:4317/logs",
                LogsExporterEnvVars.OTLP_LOGS_TIMEOUT to "2"
            )
        )
        assertEquals(
            LogRecordProcessorBehavior(
                http = OtlpHttpExporterBehavior(
                    endpoint = "http://localhost:4317/logs",
                    timeout = 2
                )
            ),
            toBehavior(configs::get),
        )
    }

    private fun env(exporter: String): (String) -> String? {
        val values = buildMap {
            put(LogsExporterEnvVars.LOGS_EXPORTER, exporter)
        }
        return values::get
    }

    private fun toBehavior(getEnvVar: (String) -> String?) =
        LogsExporterEnvVars(
            reportingEnvVarReader(getEnvVar),
        ).toBehavior()
}
