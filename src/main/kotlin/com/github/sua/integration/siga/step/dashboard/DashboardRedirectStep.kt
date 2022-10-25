package com.github.sua.integration.siga.step.dashboard

import com.github.sua.integration.siga.extraction.extractor.dashboard.DashboardRedirectExtractorRequest
import com.github.sua.integration.siga.step.StepResponse
import com.github.sua.integration.http.ConnectorHttpClient
import com.github.sua.integration.siga.step.Step

class DashboardRedirectStep(
    private val connectorClient: ConnectorHttpClient
) : Step() {

    fun doRequest(request: DashboardRedirectExtractorRequest): StepResponse {
        val headers = mapOf(
            "Cookie" to request.sessionId,
        )

        val body = mapOf(
            "javax.faces.partial.ajax" to "true",
            "javax.faces.source" to "formRedirect:j_idt32",
            "javax.faces.partial.execute" to "formRedirect:j_idt32",
            "javax.faces.partial.render" to "@none",
            "formRedirect:j_idt32" to "formRedirect:j_idt32",
            "formRedirect" to "formRedirect",
            "javax.faces.ViewState" to request.viewState,
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