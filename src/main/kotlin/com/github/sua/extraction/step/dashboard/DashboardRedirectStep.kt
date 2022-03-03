package com.github.sua.extraction.step.dashboard

import com.github.sua.extraction.extractor.dto.DefaultExtractorParams
import com.github.sua.extraction.step.StepResponse
import com.github.sua.extraction.misc.httpclient.ConnectorHttpClient
import com.github.sua.extraction.step.Step

class DashboardRedirectStep(
    private val connectorClient: ConnectorHttpClient
) : Step() {

    fun doRequest(params: DefaultExtractorParams): StepResponse {
        val headers = mapOf(
            "Cookie" to params.sessionId,
        )

        val body = mapOf(
            "javax.faces.partial.ajax" to "true",
            "javax.faces.source" to "formRedirect:j_idt31",
            "javax.faces.partial.execute" to "formRedirect:j_idt31",
            "javax.faces.partial.render" to "@none",
            "formRedirect:j_idt31" to "formRedirect:j_idt31",
            "formRedirect" to "formRedirect",
            "javax.faces.ViewState" to params.viewState,
        )

        return connectorClient.post(
            endpoint = DASHBOARD_REDIRECT_STEP_URL,
            headers = headers,
            body = body
        ).getStepResponse()
    }


    companion object {
        const val DASHBOARD_REDIRECT_STEP_URL = "sigaSecurityG5/redirectApp.jsf"
    }

}