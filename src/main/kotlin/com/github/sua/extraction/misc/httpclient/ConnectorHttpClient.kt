package com.github.sua.extraction.misc.httpclient

interface ConnectorHttpClient {

    fun get(
        endpoint: String,
        headers: Map<String, String> = emptyMap()
    ): ConnectorHttpResponse

    fun post(
        endpoint: String,
        headers: Map<String, String> = emptyMap(),
        body: Any
    ): ConnectorHttpResponse

}