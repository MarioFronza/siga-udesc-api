package com.github.sua.extraction.extractor.dashboard

import com.github.sua.extraction.exception.ExtractorException
import com.github.sua.extraction.step.dashboard.DashboardRedirectStep
import com.github.sua.extraction.step.StepResponse.StepSuccess

class DashboardRedirectExtractor(
    private val dashboardRedirectStep: DashboardRedirectStep,
) {

    fun extract(request: DashboardRedirectExtractorRequest): DashboardRedirectExtractorResponse {
        return when (val response = dashboardRedirectStep.doRequest(request)) {
            is StepSuccess -> DashboardRedirectExtractorResponse(
                endpoint = response.getEndpoint(),
            )
            else -> throw ExtractorException("Dashboard redirect extractor unexpected error")
        }
    }

}

data class DashboardRedirectExtractorRequest(
    val sessionId: String,
    val viewState: String,
)

data class DashboardRedirectExtractorResponse(
    val endpoint: String,
)