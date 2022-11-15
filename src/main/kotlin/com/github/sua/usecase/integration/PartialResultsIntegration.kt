package com.github.sua.usecase.integration


import com.github.sua.usecase.integration.dto.IntegrationOutput
import com.github.sua.usecase.retrieve.dto.input.PartialResultsIntegrationInput
import com.github.sua.usecase.retrieve.dto.output.StudentPartialResultsIntegrationOutput


interface PartialResultsIntegration {

    fun integrate(input: PartialResultsIntegrationInput): IntegrationOutput<StudentPartialResultsIntegrationOutput>

}