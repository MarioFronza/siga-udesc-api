package com.github.sua.usecase.retrieve

import com.github.sua.usecase.integration.SemesterResultsIntegration
import com.github.sua.usecase.integration.dto.IntegrationOutput.IntegrationSuccess
import com.github.sua.usecase.integration.dto.IntegrationOutput.IntegrationError
import com.github.sua.usecase.integration.dto.IntegrationOutput
import com.github.sua.usecase.retrieve.dto.output.RetrieveOutput
import com.github.sua.usecase.retrieve.dto.output.RetrieveOutput.RetrieveSuccess
import com.github.sua.usecase.retrieve.dto.output.RetrieveOutput.RetrieveError
import com.github.sua.usecase.retrieve.dto.input.SemesterResultsIntegrationInput
import com.github.sua.usecase.retrieve.dto.output.RetrieveOutput.Companion.createOutputFromIntegrationResponse
import com.github.sua.usecase.retrieve.dto.output.StudentSemesterResultsIntegrationOutput

class RetrieveSemesterResults(
    private val semesterResultsIntegration: SemesterResultsIntegration
) {

    fun retrieve(input: SemesterResultsIntegrationInput): RetrieveOutput<StudentSemesterResultsIntegrationOutput> {
        val integrationResponse = semesterResultsIntegration.integrate(input)
        return createOutputFromIntegrationResponse(integrationResponse)
    }

}

