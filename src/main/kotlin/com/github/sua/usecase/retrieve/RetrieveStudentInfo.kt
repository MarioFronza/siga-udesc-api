package com.github.sua.usecase.retrieve

import com.github.sua.usecase.integration.StudentInfoIntegration
import com.github.sua.usecase.retrieve.dto.input.SigaCredentialInput
import com.github.sua.usecase.retrieve.dto.output.RetrieveOutput
import com.github.sua.usecase.retrieve.dto.output.RetrieveOutput.Companion.createOutputFromIntegrationResponse
import com.github.sua.usecase.retrieve.dto.output.StudentInfoOutput

class RetrieveStudentInfo(
    private val studentInfoIntegration: StudentInfoIntegration
) {

    fun retrieve(input: SigaCredentialInput): RetrieveOutput<StudentInfoOutput> {
        val integrationResponse = studentInfoIntegration.integrate(input)
        return createOutputFromIntegrationResponse(integrationResponse)
    }

}