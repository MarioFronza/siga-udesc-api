package com.github.sua.extraction.extractor.login.dto

import com.github.sua.extraction.extractor.dto.DefaultExtractorParams

data class LoginExtractorParams(
    val studentCpf: String,
    val studentPassword: String,
    val defaultParams: DefaultExtractorParams
)