package com.github.sua.integration.siga.extraction.extractor.results.partial

import com.github.sua.integration.exception.ExtractorException
import com.github.sua.integration.siga.extraction.extractor.results.semester.SemesterResultsExtractorRequest
import com.github.sua.integration.siga.parser.results.PartialResultsParser
import com.github.sua.integration.siga.step.StepResponse.StepSuccess
import com.github.sua.integration.siga.step.results.partial.DashboardPartialResultsStep

class DashboardPartialResultsExtractor(
    private val dashboardPartialResultsStep: DashboardPartialResultsStep,
    private val partialResultsParser: PartialResultsParser
) {

    fun extract(request: DashboardPartialResultsExtractorRequest): DashboardPartialResultsExtractorResponse {
        return when (val response = dashboardPartialResultsStep.doRequest(request)){
            is StepSuccess -> DashboardPartialResultsExtractorResponse(
                endpoint = partialResultsParser.extractPartialResultsUrl(response.payload)
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
){
    fun toPartialResultsExtractorRequest(sessionId: String, etts: String) = PartialResultsExtractorRequest(
        endpoint = endpoint,
        sessionId = sessionId,
        etts = etts
    )
}