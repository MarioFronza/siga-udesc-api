package com.github.sua.usecase.dto.input.extraction

import com.github.sua.usecase.dto.input.credential.SigaCredentialInput
import com.github.sua.usecase.dto.input.period.PeriodInput

data class StudentInfoInput(
    val sigaCredential: SigaCredentialInput,
    val period: PeriodInput,
    val course: String,
){
    fun toDashboardInput() = DashboardInput(
        sigaCredential = sigaCredential
    )
}