package com.github.sua.integration.http

data class ConnectorHttpResponse(
    val responseContentType: String?,
    val statusCode: Int,
    val headers: Map<String, List<String>>,
    val body: String?,
    val isSuccessful: Boolean
)