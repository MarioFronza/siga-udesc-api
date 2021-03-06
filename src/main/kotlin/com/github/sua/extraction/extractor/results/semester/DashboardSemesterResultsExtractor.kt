package com.github.sua.extraction.extractor.results.semester

import com.github.sua.extraction.exception.ExtractorException
import com.github.sua.extraction.parser.results.SemesterResultsParser
import com.github.sua.extraction.step.StepResponse.StepSuccess
import com.github.sua.extraction.step.results.semester.DashboardSemesterResultsStep

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
)