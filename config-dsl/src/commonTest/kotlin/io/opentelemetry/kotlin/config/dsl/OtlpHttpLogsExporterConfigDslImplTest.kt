package io.opentelemetry.kotlin.config.dsl

import io.opentelemetry.kotlin.behavior.OtlpHttpLogsExporterBehavior
import kotlin.test.Test
import kotlin.test.assertEquals

class OtlpHttpLogsExporterConfigDslImplTest {
    @Test
    fun startsUnset(){
        val dsl = OtlpHttpLogsExporterConfigDslImpl()
        assertEquals(
            OtlpHttpLogsExporterBehavior(),
            dsl.toBehavior()
        )
    }

    @Test
    fun mapsAllFields(){
        val endpoint = "http://www.example.com:8080/logs"
        val timeout = 1_000L
        val headers = mapOf("key" to "value")
        val dsl = OtlpHttpLogsExporterConfigDslImpl()
        dsl.endpoint(endpoint)
        dsl.timeout(timeout)
        dsl.headers(headers)
        assertEquals(
            OtlpHttpLogsExporterBehavior(endpoint = endpoint, timeout = timeout, headers = headers),
            dsl.toBehavior()
        )
    }
}