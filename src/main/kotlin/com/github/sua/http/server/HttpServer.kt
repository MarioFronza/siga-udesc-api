package com.github.sua.http.server

import io.ktor.server.netty.EngineMain

object HttpServer {
    fun start(args: Array<String>) = EngineMain.main(args)
}