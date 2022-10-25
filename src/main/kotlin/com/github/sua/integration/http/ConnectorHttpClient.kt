package com.github.sua.integration.http

interface ConnectorHttpClient {

    fun get(
        endpoint: String,
        headers: Map<String, String> = emptyMap()
    ): ConnectorHttpResponse

    fun post(
        endpoint: String,
        headers: Map<String, String> = emptyMap(),
        body: Map<String, String>
    ): ConnectorHttpResponse

}