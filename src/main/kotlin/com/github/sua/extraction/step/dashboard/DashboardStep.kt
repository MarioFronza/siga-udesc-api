package com.github.sua.extraction.step.dashboard

import com.github.sua.extraction.extractor.dashboard.DashboardExtractorRequest
import com.github.sua.extraction.step.StepResponse
import com.github.sua.extraction.misc.httpclient.ConnectorHttpClient
import com.github.sua.extraction.step.Step

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