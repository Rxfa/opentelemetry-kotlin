package io.opentelemetry.kotlin.config.yaml

import io.opentelemetry.kotlin.ExperimentalApi
import io.opentelemetry.kotlin.behavior.ResourceBehavior
import io.opentelemetry.kotlin.config.schema.model.Resource

/**
 * Maps the `resource` section of a configuration file onto the behavior it supplies.
 */
@ExperimentalApi
fun Resource.toBehavior(): ResourceBehavior = TODO()