package com.github.sua.extraction.misc.httpclient

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.headers
import io.ktor.client.request.header
import io.ktor.client.utils.EmptyContent

class KtorHttpClient {

    val client = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    suspend inline fun <reified T> get(
        url: String,
        headers: Map<String, String> = emptyMap(),
    ): T = client.get(url) {
        headers {
            headers.forEach {
                header(it.key, it.value)
            }
        }
    }

    suspend inline fun <reified T> post(
        url: String,
        headers: Map<String, String> = emptyMap(),
        body: Any = EmptyContent
    ): T = client.post(url) {
        headers {
            headers.forEach {
                header(it.key, it.value)
            }
        }
        this.body = body
    }

}