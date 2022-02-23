package com.github.sua.extraction.misc.httpclient.ktor

import com.github.sua.extraction.misc.httpclient.ExtractorHttpClient
import com.github.sua.extraction.misc.httpclient.StepHttpResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking

class KtorHttpClient : ExtractorHttpClient {

    private val client = HttpClient()

    override fun get(endpoint: String, headers: Map<String, String>): StepHttpResponse {
        return runBlocking {
            val httpResponse: HttpResponse = client.get(SIGA_BASE_URL + endpoint) {
                headers {
                    headers.forEach {
                        header(it.key, it.value)
                    }
                }
            }
            val responseString = httpResponse.receive<String>()
            httpResponse.toCustomHttpResponse(body = responseString)
        }
    }

    override fun post(endpoint: String, headers: Map<String, String>, body: String): StepHttpResponse {
        return runBlocking {
            val httpResponse: HttpResponse = client.post(SIGA_BASE_URL + endpoint) {
                headers {
                    headers.forEach {
                        header(it.key, it.value)
                    }
                }
                this.body = body
            }
            val responseString = httpResponse.receive<String>()
            httpResponse.toCustomHttpResponse(body = responseString)
        }
    }

    companion object {
        const val SIGA_BASE_URL = "https://siga.udesc.br/"
    }

}