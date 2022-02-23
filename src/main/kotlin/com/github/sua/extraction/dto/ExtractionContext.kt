package com.github.sua.extraction.dto

data class ExtractionContext(
    val url: String,
    val viewState: String,
    val headerSessionId: String,
    var etts: String,
    var etp: String
)