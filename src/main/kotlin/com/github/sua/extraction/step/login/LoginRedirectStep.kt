package com.github.sua.extraction.step.login

import com.github.sua.extraction.extractor.dto.DefaultExtractorParams
import com.github.sua.extraction.step.StepResponse
import com.github.sua.extraction.misc.httpclient.ConnectorHttpClient
import com.github.sua.extraction.step.Step

class LoginRedirectStep(
    private val connectorClient: ConnectorHttpClient
) : Step() {

    fun doRequest(params: DefaultExtractorParams): StepResponse {
        val headers = mapOf(
            "Cookie" to params.sessionId,
        )

        return connectorClient.get(
            endpoint = params.endpoint,
            headers = headers
        ).getStepResponse()
    }

}