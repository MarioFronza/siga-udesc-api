package com.github.sua.extraction.misc.httpclient

data class ConnectorHttpResponse(
    val responseContentType: String?,
    val statusCode: Int,
    val headers: Map<String, String>,
    val body: String?,
    val isSuccessful: Boolean
)