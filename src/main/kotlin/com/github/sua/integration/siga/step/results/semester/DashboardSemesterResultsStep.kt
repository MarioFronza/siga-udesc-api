package com.github.sua.integration.siga.step.results.semester

import com.github.sua.integration.siga.extraction.extractor.results.semester.DashboardSemesterResultsExtractorRequest
import com.github.sua.integration.http.ConnectorHttpClient
import com.github.sua.integration.siga.step.Step
import com.github.sua.integration.siga.step.StepResponse

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