package com.github.sua.integration.siga.step.results.partial

import com.github.sua.integration.siga.extraction.extractor.results.partial.PartialResultsExtractorRequest
import com.github.sua.integration.http.ConnectorHttpClient
import com.github.sua.integration.siga.step.Step
import com.github.sua.integration.siga.step.StepResponse

class PartialResultsStep(
    private val httpClient: ConnectorHttpClient
) : Step() {

    fun doRequest(request: PartialResultsExtractorRequest): StepResponse {
        val headers = mapOf(
            "Cookie" to "${request.sessionId}; ${request.etts}"
        )

        return httpClient.get(
            endpoint = request.endpoint,
            headers = headers
        ).getStepResponse()
    }

}