package com.github.sua.usecase.retrieve

import com.github.sua.usecase.integration.SemesterResultsIntegration
import com.github.sua.usecase.integration.dto.IntegrationOutput.IntegrationSuccess
import com.github.sua.usecase.integration.dto.IntegrationOutput.IntegrationError
import com.github.sua.usecase.integration.dto.IntegrationOutput
import com.github.sua.usecase.retrieve.dto.RetrieveOutput
import com.github.sua.usecase.retrieve.dto.RetrieveOutput.RetrieveSuccess
import com.github.sua.usecase.retrieve.dto.RetrieveOutput.RetrieveError
import com.github.sua.usecase.retrieve.dto.SemesterResultsIntegrationInput
import com.github.sua.usecase.retrieve.dto.StudentSemesterResultsIntegrationOutput

class RetrieveSemesterResults(
    private val semesterResultsIntegration: SemesterResultsIntegration
) {

    fun retrieve(input: SemesterResultsIntegrationInput): RetrieveOutput<StudentSemesterResultsIntegrationOutput> {
        val integrationResponse = semesterResultsIntegration.integrate(input)
        return createOutput(integrationResponse)
    }

    private fun createOutput(integrationResponse: IntegrationOutput<StudentSemesterResultsIntegrationOutput>) =
        when (integrationResponse) {
            is IntegrationSuccess -> RetrieveSuccess(integrationResponse.data)
            is IntegrationError -> RetrieveError(integrationResponse.message)
        }
}

