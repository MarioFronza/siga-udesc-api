package com.github.sua.usecase.dto.input.extraction

import com.github.sua.usecase.dto.input.credential.SigaCredentialInput
import com.github.sua.usecase.dto.input.period.PeriodInput

data class SemesterResultsInput(
    val sigaCredential: SigaCredentialInput,
    val period: PeriodInput
){

    fun toDashboardInput() = DashboardInput(
        sigaCredential = sigaCredential
    )

}