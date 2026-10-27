package io.opentelemetry.kotlin.config.dsl

import io.opentelemetry.kotlin.behavior.OtlpHttpSpanExporterBehavior
import kotlin.test.Test
import kotlin.test.assertEquals

internal class OtlpHttpSpanExporterConfigDslImplTest {
    @Test
    fun startsUnset(){
        val dsl = OtlpHttpSpanExporterConfigDslImpl()
        assertEquals(
            OtlpHttpSpanExporterBehavior(),
            dsl.toBehavior()
        )
    }

    @Test
    fun mapsAllFields(){
        val endpoint = "http://www.example.com:8080/logs"
        val timeout = 1_000L
        val headers = mapOf("key" to "value")
        val dsl = OtlpHttpSpanExporterConfigDslImpl()
        dsl.endpoint(endpoint)
        dsl.timeout(timeout)
        dsl.headers(headers)
        assertEquals(
            OtlpHttpSpanExporterBehavior(endpoint = endpoint, timeout = timeout, headers = headers),
            dsl.toBehavior()
        )
    }
}