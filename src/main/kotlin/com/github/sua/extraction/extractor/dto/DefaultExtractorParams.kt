package com.github.sua.extraction.extractor.dto

data class DefaultExtractorParams(
    val endpoint: String = "",
    val viewState: String = "",
    val sessionId: String = "",
    val etts: String = "",
)