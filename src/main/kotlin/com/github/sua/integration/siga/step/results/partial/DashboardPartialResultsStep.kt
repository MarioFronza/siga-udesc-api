package com.github.sua.integration.siga.step.results.partial

import com.github.sua.integration.siga.extraction.extractor.results.partial.DashboardPartialResultsExtractorRequest
import com.github.sua.integration.http.ConnectorHttpClient
import com.github.sua.integration.siga.step.Step
import com.github.sua.integration.siga.step.StepResponse

class DashboardPartialResultsStep(
    private val httpClient: ConnectorHttpClient
): Step() {

    fun doRequest(request: DashboardPartialResultsExtractorRequest): StepResponse {
        val headers = mapOf(
            "Cookie" to "${request.sessionId}; ECS=S; ${request.etts}"
        )

        return httpClient.get(
            endpoint = request.endpoint,
            headers = headers
        ).getStepResponse()
    }

}