package com.github.sua.extraction.step.login

import com.github.sua.extraction.misc.httpclient.ExtractorHttpClient
import com.github.sua.extraction.misc.httpclient.StepHttpResponse
import com.github.sua.extraction.step.Step
import com.github.sua.extraction.step.dto.LoginStepInput

class LoginStep(
    private val connectorClient: ExtractorHttpClient
) : Step() {

    fun doRequest(input: LoginStepInput): StepHttpResponse {
        val headers = mapOf(
            "Cookie" to input.headerSessionId,
        )

        val body = mapOf(
            "javax.faces.partial.ajax" to "true",
            "javax.faces.source" to "loginForm:realizaLogin",
            "javax.faces.partial.execute" to "loginForm:realizaLogin",
            "javax.faces.partial.render" to "loginForm:realizaLogin",
            "loginForm:realizaLogin" to "loginForm:realizaLogin",
            "tipoLogin" to "PADRAO",
            "login" to input.studentCpf,
            "senha" to input.studentPassword,
            "mantemConectado" to "S",
            "loginForm" to "loginForm",
            "javax.faces.ViewState" to input.viewState,
        )

        return connectorClient.post(
            endpoint = input.url,
            headers = headers,
            body = body.toString()
        )
    }

}