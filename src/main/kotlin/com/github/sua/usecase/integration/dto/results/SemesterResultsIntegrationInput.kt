package com.github.sua.usecase.integration.dto.results

import com.github.sua.usecase.integration.dto.credential.SigaCredentialInput
import com.github.sua.usecase.integration.dto.period.PeriodInput


data class SemesterResultsIntegrationInput(
    val sigaCredential: SigaCredentialInput,
    val period: PeriodInput
)
