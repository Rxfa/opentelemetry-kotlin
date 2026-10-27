package io.opentelemetry.kotlin.config.dsl

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.behavior.OtlpHttpLogsExporterBehavior

/**
 * Captures the OTLP HTTP logs exporter configured programmatically and maps it onto a behavior.
 */
@ExperimentalApi
class OtlpHttpLogsExporterConfigDslImpl: BehaviorSupplier<OtlpHttpLogsExporterBehavior> {
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

    override fun toBehavior(): OtlpHttpLogsExporterBehavior {
        return OtlpHttpLogsExporterBehavior(endpoint, timeout, headers)
    }

}