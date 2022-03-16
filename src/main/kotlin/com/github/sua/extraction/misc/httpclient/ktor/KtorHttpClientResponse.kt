package com.github.sua.extraction.misc.httpclient.ktor

import com.github.sua.extraction.misc.httpclient.ConnectorHttpResponse
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*

fun HttpResponse.toCustomHttpResponse(body: String) = ConnectorHttpResponse(
    responseContentType = contentType().toString(),
    statusCode = status.value,
    headers = headers.flattenEntries().groupBy({ it.first }, { it.second }),
    body = body,
    isSuccessful = status.value in 200..299
)