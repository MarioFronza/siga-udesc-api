package com.github.sua.extraction.step.results.partial

import com.github.sua.extraction.extractor.results.partial.DashboardPartialResultsExtractorRequest
import com.github.sua.extraction.misc.httpclient.ConnectorHttpClient
import com.github.sua.extraction.step.Step
import com.github.sua.extraction.step.StepResponse

class DashboardPartialResultsStep(
    private val httpClient: ConnectorHttpClient
): Step() {

    fun doRequest(request: DashboardPartialResultsExtractorRequest): StepResponse{
        val headers = mapOf(
            "Cookie" to "${request.sessionId}; ECS=S; ${request.etts}"
        )

        return httpClient.get(
            endpoint = request.endpoint,
            headers = headers
        ).getStepResponse()
    }

}