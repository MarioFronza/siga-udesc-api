package com.github.sua.integration.siga.extraction.dto

import com.github.sua.usecase.retrieve.dto.input.PartialResultsIntegrationInput
import com.github.sua.usecase.retrieve.dto.input.PeriodInput
import com.github.sua.usecase.retrieve.dto.input.SigaCredentialInput
import com.github.sua.usecase.retrieve.dto.input.SemesterResultsIntegrationInput

data class DashboardInput(
    val sigaCredential: SigaCredentialInput,
    val cpf: String,
    val password: String,
) {


    companion object {
        fun fromSemesterResultsIntegrationInput(input: SemesterResultsIntegrationInput) = DashboardInput(
            sigaCredential = input.sigaCredential,
            cpf = input.sigaCredential.cpf,
            password = input.sigaCredential.password
        )

        fun fromPartialResultsIntegrationInput(input: PartialResultsIntegrationInput) = DashboardInput(
            sigaCredential = input.sigaCredential,
            cpf = input.sigaCredential.cpf,
            password = input.sigaCredential.password
        )

        fun fromSigaCredentialInput(input: SigaCredentialInput) = DashboardInput(
            sigaCredential = input,
            cpf = input.cpf,
            password = input.password
        )
    }
}