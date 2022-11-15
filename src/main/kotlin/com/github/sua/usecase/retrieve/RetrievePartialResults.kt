package com.github.sua.usecase.retrieve

import com.github.sua.usecase.retrieve.dto.input.PartialResultsIntegrationInput
import com.github.sua.usecase.integration.PartialResultsIntegration
import com.github.sua.usecase.retrieve.dto.output.RetrieveOutput
import com.github.sua.usecase.retrieve.dto.output.RetrieveOutput.Companion.createOutputFromIntegrationResponse
import com.github.sua.usecase.retrieve.dto.output.StudentPartialResultsIntegrationOutput

class RetrievePartialResults(
    private val partialResultsIntegration: PartialResultsIntegration
) {

    fun retrieve(input: PartialResultsIntegrationInput): RetrieveOutput<StudentPartialResultsIntegrationOutput> {
        val integrationResponse = partialResultsIntegration.integrate(input)
        return createOutputFromIntegrationResponse(integrationResponse)
    }
}
