package com.github.sua.usecase.dto.input.extraction

import com.github.sua.usecase.retrieve.dto.input.SigaCredentialInput
import com.github.sua.usecase.retrieve.dto.input.PeriodInput

data class StudentInfoInput(
    val sigaCredential: SigaCredentialInput,
    val period: PeriodInput,
    val course: String,
){
    fun toDashboardInput() = DashboardInput(
        sigaCredential = sigaCredential,
        cpf = "",
        password = ""
    )
}