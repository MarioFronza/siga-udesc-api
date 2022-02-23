package com.github.sua.extraction.step.login

import com.github.sua.extraction.misc.httpclient.ExtractorHttpClient
import com.github.sua.extraction.step.Step

class CookieStep(
    private val connectorClient: ExtractorHttpClient,
) : Step() {

    fun doRequest() = connectorClient.get(COOKIE_STEP_URL, emptyMap()).getStepResponse()

    companion object {
        const val COOKIE_STEP_URL = "sigaSecurityG5"
    }
}