package com.github.sua.integration.siga.extraction.extractor.dashboard

import com.github.sua.integration.exception.ExtractorException
import com.github.sua.integration.siga.parser.dashboard.DashboardParser
import com.github.sua.integration.siga.step.dashboard.DashboardStep
import com.github.sua.integration.siga.step.StepResponse.StepSuccess

class DashboardExtractor(
    private val dashboardStep: DashboardStep,
    private val dashboardParser: DashboardParser
) {

    fun extract(request: DashboardExtractorRequest, resultPageType: String): DashboardExtractorResponse {
        return when (val response = dashboardStep.doRequest(request)) {
            is StepSuccess -> DashboardExtractorResponse(
                studentName = dashboardParser.extractStudentName(response.payload),
                endpoint = dashboardParser.extractDashboardActionPageUrl(response.payload, resultPageType)
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
