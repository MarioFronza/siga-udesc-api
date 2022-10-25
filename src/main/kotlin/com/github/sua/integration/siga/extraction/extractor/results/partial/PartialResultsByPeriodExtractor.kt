package com.github.sua.integration.siga.extraction.extractor.results.partial

import com.github.sua.integration.exception.ExtractorException
import com.github.sua.usecase.dto.output.extraction.PartialResults
import com.github.sua.integration.siga.parser.results.PartialResultsParser
import com.github.sua.integration.siga.step.StepResponse.StepSuccess
import com.github.sua.integration.siga.step.results.partial.PartialResultsByPeriodStep

class PartialResultsByPeriodExtractor(
    private val partialResultsByPeriodStep: PartialResultsByPeriodStep,
    private val partialResultsParser: PartialResultsParser
) {

    fun extract(request: PartialResultsByPeriodExtractorRequest): PartialResultsByPeriodExtractorResponse {
        return when (val response = partialResultsByPeriodStep.doRequest(request)) {
            is StepSuccess -> PartialResultsByPeriodExtractorResponse(
                partialResults = partialResultsParser.extractPartialResults(
                    response.payload,
                    request.periodIdentified,
                    request.courseIdentified,
                    request.subjectIdentified,
                )
            )
            else -> throw ExtractorException("Partial results extractor unexpected error")
        }
    }

}

data class PartialResultsByPeriodExtractorRequest(
    val sessionId: String,
    val etts: String,
    val periodIdentified: String,
    val courseIdentified: String,
    val subjectIdentified: String
)

data class PartialResultsByPeriodExtractorResponse(
    val partialResults: PartialResults,
)