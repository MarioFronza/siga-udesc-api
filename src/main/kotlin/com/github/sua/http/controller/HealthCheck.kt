package com.github.sua.http.controller

import io.ktor.application.call
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.*
import io.ktor.routing.Route


@Location("/health")
class HealthCheck

fun Route.healthCheck() = get<HealthCheck> {
    call.respond(mapOf("status" to "ok"))
}