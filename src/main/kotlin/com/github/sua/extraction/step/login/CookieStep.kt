package com.github.sua.extraction.step.login

import com.github.sua.extraction.misc.httpclient.ConnectorHttpClient
import com.github.sua.extraction.step.Step

class CookieStep(
    private val connectorClient: ConnectorHttpClient,
) : Step() {

    fun doRequest() = connectorClient.get(COOKIE_STEP_URL).getStepResponse()

    companion object {
        const val COOKIE_STEP_URL = "sigaSecurityG5"
    }
}