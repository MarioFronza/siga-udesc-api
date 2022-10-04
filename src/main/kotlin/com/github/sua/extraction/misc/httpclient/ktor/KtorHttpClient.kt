package com.github.sua.extraction.misc.httpclient.ktor

import com.github.sua.extraction.misc.httpclient.ConnectorHttpClient
import com.github.sua.extraction.misc.httpclient.ConnectorHttpResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking

class KtorHttpClient : ConnectorHttpClient {

    private val client = HttpClient()

    override fun get(endpoint: String, headers: Map<String, String>): ConnectorHttpResponse {
        return runBlocking {
            val httpResponse: HttpResponse = client.get(SIGA_BASE_URL + endpoint) {
                headers {
                    headers.forEach {
                        header(it.key, it.value)
                    }
                }
            }
            val responseString = httpResponse.body<String>()
            httpResponse.toCustomHttpResponse(body = responseString)
        }
    }

    override fun post(
        endpoint: String,
        headers: Map<String, String>,
        body: Map<String, String>
    ): ConnectorHttpResponse {
        return runBlocking {
            val httpResponse: HttpResponse = client.post(SIGA_BASE_URL + endpoint) {
                headers {
                    headers.forEach {
                        header(it.key, it.value)
                    }
                }
                setBody(FormDataContent(Parameters.build {
                    body.entries.forEach {
                        append(it.key, it.value)
                    }
                }))
            }
            val responseString = httpResponse.body<String>()
            httpResponse.toCustomHttpResponse(body = responseString)
        }
    }

    companion object {
        const val SIGA_BASE_URL = "https://siga.udesc.br/"
    }

}