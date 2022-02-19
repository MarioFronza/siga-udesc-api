package com.github.sua.http.extension

import io.ktor.application.*
import io.ktor.features.*

fun ApplicationCall.getRequiredParameter(name: String): String {
    return parameters[name] ?: throw BadRequestException("Missing required parameters")
}

