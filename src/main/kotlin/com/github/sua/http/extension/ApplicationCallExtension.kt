package com.github.sua.http.extension

import io.ktor.server.application.*
import io.ktor.server.plugins.*

fun ApplicationCall.getRequiredParameter(name: String): String {
    return parameters[name] ?: throw BadRequestException("Missing required parameters")
}

