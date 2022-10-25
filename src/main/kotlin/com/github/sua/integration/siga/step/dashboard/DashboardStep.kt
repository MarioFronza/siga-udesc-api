package com.github.sua.integration.siga.step.dashboard

import com.github.sua.integration.siga.extraction.extractor.dashboard.DashboardExtractorRequest
import com.github.sua.integration.siga.step.StepResponse
import com.github.sua.integration.http.ConnectorHttpClient
import com.github.sua.integration.siga.step.Step

class DashboardStep(
    private val connectorClient: ConnectorHttpClient
) : Step() {

    fun doRequest(params: DashboardExtractorRequest): StepResponse {
        val headers = mapOf(
            "Cookie" to "${params.sessionId}; ECS=S; ${params.etts}"
        )

        return connectorClient.get(
            endpoint = params.endpoint,
            headers = headers
        ).getStepResponse()
    }

}