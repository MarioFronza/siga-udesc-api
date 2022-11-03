package com.github.sua.usecase.integration


import com.github.sua.usecase.integration.dto.IntegrationOutput
import com.github.sua.usecase.retrieve.dto.SemesterResultsIntegrationInput
import com.github.sua.usecase.retrieve.dto.StudentSemesterResultsIntegrationOutput


interface SemesterResultsIntegration {

    fun integrate(input: SemesterResultsIntegrationInput): IntegrationOutput<StudentSemesterResultsIntegrationOutput>

}