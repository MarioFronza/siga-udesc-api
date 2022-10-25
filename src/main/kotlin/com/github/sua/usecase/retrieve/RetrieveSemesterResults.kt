package com.github.sua.usecase.retrieve

import com.github.sua.usecase.integration.SemesterResultsIntegration
import com.github.sua.usecase.integration.dto.IntegrationOutput
import com.github.sua.usecase.integration.dto.results.SemesterResultsIntegrationInput
import com.github.sua.usecase.integration.dto.results.StudentSemesterResultsIntegrationOutput

class RetrieveSemesterResults(private val semesterResultsIntegration: SemesterResultsIntegration) {

    fun retrieve(input: SemesterResultsIntegrationInput): StudentSemesterResultsIntegrationOutput {
        val result =
                semesterResultsIntegration.integrate(input) as IntegrationOutput.IntegrationSuccess
        return result.data
    }
}
