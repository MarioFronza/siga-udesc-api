package com.github.sua.extraction.extractor.dashboard

import com.github.sua.extraction.exception.ExtractorException
import com.github.sua.extraction.parser.dashboard.DashboardParser
import com.github.sua.extraction.step.dashboard.DashboardStep
import com.github.sua.extraction.step.StepResponse.StepSuccess

class DashboardExtractor(
    private val dashboardStep: DashboardStep,
    private val dashboardParser: DashboardParser
) {

    fun extract(request: DashboardExtractorRequest, pageType: String): DashboardExtractorResponse {
        return when (val response = dashboardStep.doRequest(request)) {
            is StepSuccess -> DashboardExtractorResponse(
                studentName = dashboardParser.extractStudentName(response.payload),
                endpoint = dashboardParser.extractDashboardActionPageUrl(response.payload, pageType)
            )
            else -> throw ExtractorException("Dashboard extractor unexpected error")
        }
    }

}

data class DashboardExtractorRequest(
    val endpoint: String,
    val sessionId: String,
    val etts: String,
)

data class DashboardExtractorResponse(
    val studentName: String,
    val endpoint: String
)

