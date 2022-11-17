package com.github.sua.usecase.integration


import com.github.sua.usecase.integration.dto.IntegrationOutput
import com.github.sua.usecase.retrieve.dto.input.SigaCredentialInput
import com.github.sua.usecase.retrieve.dto.output.StudentInfoOutput


interface StudentInfoIntegration {

    fun integrate(input: SigaCredentialInput): IntegrationOutput<StudentInfoOutput>

}