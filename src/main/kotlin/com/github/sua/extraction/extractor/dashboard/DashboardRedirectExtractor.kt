package com.github.sua.extraction.extractor.dashboard

import com.github.sua.extraction.exception.ExtractorException
import com.github.sua.extraction.extractor.dto.DefaultExtractorParams
import com.github.sua.extraction.step.dashboard.DashboardRedirectStep
import com.github.sua.extraction.step.StepResponse.StepSuccess

class DashboardRedirectExtractor(
    private val dashboardRedirectStep: DashboardRedirectStep,
) {

    fun extract(params: DefaultExtractorParams): DefaultExtractorParams {
        return when (val response = dashboardRedirectStep.doRequest(params)) {
            is StepSuccess -> DefaultExtractorParams(
                sessionId = response.getSessionId(),
                viewState = response.getViewState()
            )
            else -> throw ExtractorException("Dashboard redirect extractor unexpected error")
        }
    }

}