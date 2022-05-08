package com.github.sua.usecase.dto.input.extraction

import com.github.sua.usecase.dto.input.credential.SigaCredentialInput
import com.github.sua.usecase.dto.input.period.PeriodInput

data class PartialResultsInput(
    val sigaCredential: SigaCredentialInput,
    val period: PeriodInput,
    val course: String,
    val subject: String,
){
    fun toDashboardInput() = DashboardInput(
        sigaCredential = sigaCredential
    )
}