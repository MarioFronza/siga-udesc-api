package com.github.sua.usecase.integration


import com.github.sua.usecase.integration.dto.IntegrationOutput
import com.github.sua.usecase.integration.dto.results.SemesterResultsIntegrationInput
import com.github.sua.usecase.integration.dto.results.StudentSemesterResultsIntegrationOutput


interface SemesterResultsIntegration {

    fun integrate(input: SemesterResultsIntegrationInput): IntegrationOutput<StudentSemesterResultsIntegrationOutput>

}