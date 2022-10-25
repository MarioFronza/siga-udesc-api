package com.github.sua.integration.siga.extraction.extractor.results.semester

import com.github.sua.integration.exception.ExtractorException
import com.github.sua.integration.siga.parser.results.SemesterResultsParser
import com.github.sua.integration.siga.step.StepResponse.StepSuccess
import com.github.sua.integration.siga.step.results.semester.DashboardSemesterResultsStep

class DashboardSemesterResultsExtractor(
    private val dashboardSemesterResultsStep: DashboardSemesterResultsStep,
    private val semesterResultsParser: SemesterResultsParser
) {

    fun extract(request: DashboardSemesterResultsExtractorRequest): DashboardSemesterResultsExtractorResponse {
        return when (val response = dashboardSemesterResultsStep.doRequest(request)) {
            is StepSuccess -> DashboardSemesterResultsExtractorResponse(
                endpoint = semesterResultsParser.extractSemesterResultsUrl(response.payload),
            )

            else -> throw ExtractorException("Semester results extractor unexpected error")
        }
    }

}

data class DashboardSemesterResultsExtractorRequest(
    val endpoint: String,
    val sessionId: String,
    val etts: String,
)

data class DashboardSemesterResultsExtractorResponse(
    val endpoint: String,
) {
    fun toSemesterResultsExtractorRequest(sessionId: String, etts: String) = SemesterResultsExtractorRequest(
        endpoint = endpoint,
        sessionId = sessionId,
        etts = etts
    )
}