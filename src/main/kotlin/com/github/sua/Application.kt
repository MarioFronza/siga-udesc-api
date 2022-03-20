package com.github.sua

import com.github.sua.http.plugins.configureDI
import com.github.sua.http.plugins.configureRouting
import com.github.sua.http.plugins.configureSerialization
import com.github.sua.http.server.HttpServer
import io.ktor.application.*

fun main(args: Array<String>) {
    HttpServer.start(args)
}

fun Application.module() {
    configureDI()
    configureSerialization()
    configureRouting()
}
