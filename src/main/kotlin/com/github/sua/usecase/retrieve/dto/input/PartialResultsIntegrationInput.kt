package com.github.sua.usecase.retrieve.dto.input


data class PartialResultsIntegrationInput(
    val sigaCredential: SigaCredentialInput,
    val period: PeriodInput,
    val course: String,
    val subject: String,
)