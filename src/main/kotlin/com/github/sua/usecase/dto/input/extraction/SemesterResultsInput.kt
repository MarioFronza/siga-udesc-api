package com.github.sua.usecase.dto.input.extraction

import com.github.sua.usecase.integration.dto.credential.SigaCredentialInput
import com.github.sua.usecase.integration.dto.period.PeriodInput

data class SemesterResultsInput(
    val sigaCredential: SigaCredentialInput,
    val period: PeriodInput
) {

    fun toDashboardInput() = DashboardInput(
        sigaCredential = sigaCredential,
        cpf = "",
        password = ""
    )

}