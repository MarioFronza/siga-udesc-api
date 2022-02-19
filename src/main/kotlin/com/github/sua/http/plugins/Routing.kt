package com.github.sua.http.plugins

import com.github.sua.http.controller.healthCheck
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.locations.*
import io.ktor.routing.*

fun Application.configureRouting() {
    install(DefaultHeaders)
    install(Locations)
    install(CallLogging)

    routing {
        healthCheck()
    }
}