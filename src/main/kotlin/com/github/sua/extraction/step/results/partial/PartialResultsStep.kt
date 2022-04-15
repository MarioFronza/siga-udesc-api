package com.github.sua.extraction.step.results.partial

import com.github.sua.extraction.extractor.results.partial.PartialResultsExtractorRequest
import com.github.sua.extraction.misc.httpclient.ConnectorHttpClient
import com.github.sua.extraction.step.Step
import com.github.sua.extraction.step.StepResponse

class PartialResultsStep(
    private val httpClient: ConnectorHttpClient
) : Step() {

    fun doRequest(request: PartialResultsExtractorRequest): StepResponse{
        val headers = mapOf(
            "Cookie" to "${request.sessionId}; ECS=S; ${request.etts}"
        )

        return httpClient.get(
            endpoint = request.endpoint,
            headers = headers
        ).getStepResponse()
    }

}