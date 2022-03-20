package com.github.sua.extraction.step.login

import com.github.sua.extraction.extractor.login.LoginRedirectExtractorRequest
import com.github.sua.extraction.step.StepResponse
import com.github.sua.extraction.misc.httpclient.ConnectorHttpClient
import com.github.sua.extraction.step.Step

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