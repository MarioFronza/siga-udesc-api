package com.github.sua.extraction.step.dto

data class LoginStepInput(
    val url: String,
    val viewState: String,
    val headerSessionId: String,
    val studentCpf: String,
    val studentPassword: String
)