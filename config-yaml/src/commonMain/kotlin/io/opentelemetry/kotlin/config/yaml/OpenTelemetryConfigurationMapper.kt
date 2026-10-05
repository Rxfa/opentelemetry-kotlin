package io.opentelemetry.kotlin.config.yaml

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.behavior.LoggerProviderBehavior
import io.opentelemetry.kotlin.behavior.OpenTelemetryBehavior
import io.opentelemetry.kotlin.behavior.TracerProviderBehavior
import io.opentelemetry.kotlin.behavior.toSeverityLevel
import io.opentelemetry.kotlin.config.schema.model.OpenTelemetryConfiguration

/**
 * Maps a declarative config file onto the behavior it supplies, as one layer.
 */
@ExperimentalApi
fun OpenTelemetryConfiguration.toBehavior(): OpenTelemetryBehavior {
    // TODO: Map [Resource] onto [ResourceBehavior]
    return OpenTelemetryBehavior(
        fileFormat = fileFormat,
        disabled = disabled ?: false,
        logLevel = logLevel?.name?.toSeverityLevel() ?: OpenTelemetryBehavior.DEFAULT_LOG_LEVEL,
        distribution = distribution,
        attributeLimits = attributeLimits?.toBehavior(),
        tracerProvider = tracerProvider?.let {
            TracerProviderBehavior(
                spanLimits = it.limits?.toBehavior(),
                processor = it.processors.toBehavior(),
                sampler = it.sampler?.toBehavior(),
                idGenerator = it.idGenerator?.toBehavior(),
            )
        },
        loggerProvider = loggerProvider?.let {
            LoggerProviderBehavior(
                logLimits = it.limits?.toBehavior(),
                processor = it.processors.toBehavior(),
            )
        },
    )
}
