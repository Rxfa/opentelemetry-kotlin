package io.opentelemetry.kotlin.behavior

import io.opentelemetry.kotlin.ExperimentalApi

@ExperimentalApi
/**
 * Selecting the OTLP HTTP exporter.
 *
 * https://opentelemetry.io/docs/specs/otel/protocol/exporter/
 */
data class OtlpHttpExporterBehavior(
    /**
     * Target to which the exporter is going to send spans, metrics, or logs.
     */
    val endpoint: String? = null,
    val tls: HttpTls? = null,
    /**
     * HTTP Request headers.
     * Entries have higher priority than entries from [headersList].
     */
    val headers: Map<String, String?>? = null,
    /**
     * HTTP Request headers.
     * Entries have lower priority than entries from [headers].
     */
    val headersList: List<String>? = null,
    /**
     * Compression algorithm used.
     */
    val compression: String? = null,
    /**
     * Maximum size of each export request body in bytes, before compression.
     */
    val maxRequestSize: Int? = null,
    /**
     * Maximum size of each export response in bytes, after decompression.
     */
    val maxResponseSize: Int? = null,
    /**
     * Maximum time (in milliseconds) to wait for each export.
     */
    val timeout: Int? = null,
    /**
     * Encoding used for messages.
     */
    val encoding: HttpEncoding? = null,
) : Behavior<OtlpHttpExporterBehavior> {
    override fun mergeWith(higher: OtlpHttpExporterBehavior): OtlpHttpExporterBehavior {
        return copy(
            endpoint = higher.endpoint ?: endpoint,
            tls = higher.tls ?: tls,
            headers = higher.headers ?: headers,
            headersList = higher.headersList ?: headersList,
            compression = higher.compression ?: compression,
            maxRequestSize = higher.maxRequestSize ?: maxRequestSize,
            maxResponseSize = higher.maxResponseSize ?: maxResponseSize,
            timeout = higher.timeout ?: timeout,
            encoding = higher.encoding ?: encoding,
        )
    }
}


data class HttpTls(
    /**
     * Absolute path to certificate file in PEM format
     */
    val caFile: String? = null,
    /**
     * Absolute path to client key file in PEM format.
     * If set, [certFile] must also be set.
     */
    val keyFile: String? = null,
    /**
     * Absolute path to client certificate file in PEM format.
     * If set, [keyFile] must also be set.
     */
    val certFile: String? = null,
)

enum class HttpEncoding {
    /**
     * Protobuf JSON encoding.
     */
    JSON,
    /**
     * Protobuf binary encoding.
     */
    PROTOBUF
}