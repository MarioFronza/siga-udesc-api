package com.github.sua.integration.siga.step.login

import com.github.sua.integration.http.ConnectorHttpClient
import com.github.sua.integration.siga.step.Step

class CookieStep(
    private val connectorClient: ConnectorHttpClient,
) : Step() {

    fun doRequest() = connectorClient.get(COOKIE_STEP_URL).getStepResponse()

    companion object {
        const val COOKIE_STEP_URL = "sigaSecurityG5"
    }
}