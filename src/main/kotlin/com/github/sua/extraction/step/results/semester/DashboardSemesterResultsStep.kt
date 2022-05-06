package com.github.sua.extraction.step.results.semester

import com.github.sua.extraction.extractor.results.semester.DashboardSemesterResultsExtractorRequest
import com.github.sua.extraction.misc.httpclient.ConnectorHttpClient
import com.github.sua.extraction.step.Step
import com.github.sua.extraction.step.StepResponse

class DashboardSemesterResultsStep(
    private val httpClient: ConnectorHttpClient
) : Step() {

    fun doRequest(request: DashboardSemesterResultsExtractorRequest): StepResponse {
        val headers = mapOf(
            "Cookie" to "${request.sessionId}; ECS=S; ${request.etts}"
        )

        return httpClient.get(
            endpoint = request.endpoint,
            headers = headers
        ).getStepResponse()
    }

}