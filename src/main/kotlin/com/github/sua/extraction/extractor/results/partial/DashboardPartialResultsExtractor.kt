package com.github.sua.extraction.extractor.results.partial

import com.github.sua.extraction.exception.ExtractorException
import com.github.sua.extraction.parser.semesterresults.PartialResultsParser
import com.github.sua.extraction.step.StepResponse.StepSuccess
import com.github.sua.extraction.step.results.partial.DashboardPartialResultsStep

class DashboardPartialResultsExtractor(
    private val dashboardPartialResultsStep: DashboardPartialResultsStep,
    private val partialResultsParser: PartialResultsParser
) {

    fun extract(request: DashboardPartialResultsExtractorRequest): DashboardPartialResultsExtractorResponse {
        return when (val response =dashboardPartialResultsStep.doRequest(request)){
            is StepSuccess -> DashboardPartialResultsExtractorResponse(
                endpoint =partialResultsParser.extractSemesterResultsUrl(response.payload)
            )
            else -> throw ExtractorException("Partial results extractor unexpected error")
        }
    }

}

data class DashboardPartialResultsExtractorRequest(
    val endpoint: String,
    val sessionId: String,
    val etts: String
)

data class DashboardPartialResultsExtractorResponse(
    val endpoint: String
)