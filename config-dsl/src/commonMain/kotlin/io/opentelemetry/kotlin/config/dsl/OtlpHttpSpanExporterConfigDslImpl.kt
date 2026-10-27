package io.opentelemetry.kotlin.config.dsl

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.behavior.OtlpHttpSpanExporterBehavior

/**
 * Captures the OTLP HTTP span exporter configured programmatically and maps it onto a behavior.
 */
@ExperimentalApi
class OtlpHttpSpanExporterConfigDslImpl: BehaviorSupplier<OtlpHttpSpanExporterBehavior> {
    private var endpoint: String? = null
    private var timeout: Long? = null
    private var headers: Map<String, String?>? = null

    fun endpoint(endpoint: String) {
        this.endpoint = endpoint
    }

    fun timeout(timeout: Long) {
        this.timeout = timeout
    }

    fun headers(headers: Map<String, String?>) {
        this.headers = headers
    }

    override fun toBehavior(): OtlpHttpSpanExporterBehavior {
        return OtlpHttpSpanExporterBehavior(endpoint, timeout, headers)
    }
}