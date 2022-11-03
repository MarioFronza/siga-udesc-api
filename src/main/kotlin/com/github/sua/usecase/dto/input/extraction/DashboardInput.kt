package com.github.sua.usecase.dto.input.extraction

import com.github.sua.usecase.integration.dto.credential.SigaCredentialInput
import com.github.sua.usecase.retrieve.dto.SemesterResultsIntegrationInput

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
    }
}