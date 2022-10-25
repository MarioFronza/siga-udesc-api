package com.github.sua.integration.siga.step.login

import com.github.sua.integration.siga.extraction.extractor.login.LoginRedirectExtractorRequest
import com.github.sua.integration.siga.step.StepResponse
import com.github.sua.integration.http.ConnectorHttpClient
import com.github.sua.integration.siga.step.Step

class LoginRedirectStep(
    private val connectorClient: ConnectorHttpClient
) : Step() {

    fun doRequest(request: LoginRedirectExtractorRequest): StepResponse {
        val headers = mapOf(
            "Cookie" to request.sessionId,
        )

        return connectorClient.get(
            endpoint = request.endpoint,
            headers = headers
        ).getStepResponse()
    }

}