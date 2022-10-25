package com.github.sua.integration.siga.step.login

import com.github.sua.integration.siga.extraction.extractor.login.LoginExtractorRequest
import com.github.sua.integration.siga.step.StepResponse
import com.github.sua.integration.http.ConnectorHttpClient
import com.github.sua.integration.siga.step.Step

class LoginStep(
    private val connectorClient: ConnectorHttpClient
) : Step() {

    fun doRequest(request: LoginExtractorRequest): StepResponse {
        val headers = mapOf(
            "Cookie" to " cookie_mentorweb_consent_accepted=YES; ${request.sessionId}",
        )

        val body = mapOf(
            "javax.faces.partial.ajax" to "true",
            "javax.faces.source" to "loginForm:realizaLogin",
            "javax.faces.partial.execute" to "loginForm:realizaLogin",
            "javax.faces.partial.render" to "loginForm:realizaLogin",
            "loginForm:realizaLogin" to "loginForm:realizaLogin",
            "tipoLogin" to "PADRAO",
            "login" to request.studentCpf,
            "senha" to request.studentPassword,
            "mantemConectado" to "S",
            "loginForm" to "loginForm",
            "javax.faces.ViewState" to request.viewState,
        )

        return connectorClient.post(
            endpoint = request.endpoint,
            headers = headers,
            body = body
        ).getStepResponse()
    }

}