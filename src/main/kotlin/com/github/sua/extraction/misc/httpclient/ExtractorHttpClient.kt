package com.github.sua.extraction.misc.httpclient

interface ExtractorHttpClient {

    fun get(
        endpoint: String,
        headers: Map<String, String> = emptyMap()
    ): StepHttpResponse

    fun post(
        endpoint: String,
        headers: Map<String, String> = emptyMap(),
        body: String
    ): StepHttpResponse

}