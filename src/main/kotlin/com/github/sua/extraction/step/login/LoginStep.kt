package com.github.sua.extraction.step.login

import com.github.sua.extraction.extractor.login.dto.LoginExtractorParams
import com.github.sua.extraction.step.StepResponse
import com.github.sua.extraction.misc.httpclient.ConnectorHttpClient
import com.github.sua.extraction.step.Step

class LoginStep(
    private val connectorClient: ConnectorHttpClient
) : Step() {

    fun doRequest(params: LoginExtractorParams): StepResponse {
        val headers = mapOf(
            "Cookie" to params.defaultParams.sessionId,
        )

        val body = mapOf(
            "javax.faces.partial.ajax" to "true",
            "javax.faces.source" to "loginForm:realizaLogin",
            "javax.faces.partial.execute" to "loginForm:realizaLogin",
            "javax.faces.partial.render" to "loginForm:realizaLogin",
            "loginForm:realizaLogin" to "loginForm:realizaLogin",
            "tipoLogin" to "PADRAO",
            "login" to params.studentCpf,
            "senha" to params.studentPassword,
            "mantemConectado" to "S",
            "loginForm" to "loginForm",
            "javax.faces.ViewState" to params.defaultParams.viewState,
        )

        return connectorClient.post(
            endpoint = params.defaultParams.endpoint,
            headers = headers,
            body = body
        ).getStepResponse()
    }

}