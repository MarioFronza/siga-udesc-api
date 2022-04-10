package com.github.sua.extraction.step.login

import com.github.sua.extraction.extractor.login.LoginExtractorRequest
import com.github.sua.extraction.misc.httpclient.ConnectorHttpClient
import com.github.sua.extraction.misc.httpclient.ConnectorHttpResponse
import com.github.sua.extraction.step.StepResponse.StepError
import com.github.sua.extraction.step.StepResponse.StepSuccess
import io.mockk.every
import io.mockk.mockk
import java.net.HttpURLConnection
import kotlin.test.Test
import kotlin.test.assertEquals


class LoginStepTest {

    private val httpClient: ConnectorHttpClient = mockk()
    private val step = LoginStep(httpClient)

    @Test
    fun `should return StepSuccess when execute successfully`() {

        every {
            httpClient.post(
                endpoint = "endpoint",
                headers = defaultHeaders,
                body = defaultBody
            )
        } returns ConnectorHttpResponse(
            responseContentType = null,
            statusCode = HttpURLConnection.HTTP_OK,
            headers = emptyMap(),
            body = "SUCCESS RESPONSE",
            isSuccessful = true
        )

        val expected = StepSuccess(payload = "SUCCESS RESPONSE", headers = emptyMap())
        val actual = step.doRequest(
            LoginExtractorRequest(
                studentCpf = "11111111111",
                studentPassword = "password",
                sessionId = "sessionId",
                endpoint = "endpoint",
                viewState = "viewState"
            )
        )

        assertEquals(expected, actual)
    }

    @Test
    fun `should return StepError when not execute successfully`() {
        every {
            httpClient.post(
                endpoint = "endpoint",
                headers = defaultHeaders,
                body = defaultBody
            )
        } returns ConnectorHttpResponse(
            responseContentType = null,
            statusCode = HttpURLConnection.HTTP_BAD_REQUEST,
            headers = emptyMap(),
            body = "ERROR RESPONSE",
            isSuccessful = false
        )

        val expected = StepError(payload = "ERROR RESPONSE")
        val actual = step.doRequest(
            LoginExtractorRequest(
                studentCpf = "11111111111",
                studentPassword = "password",
                sessionId = "sessionId",
                endpoint = "endpoint",
                viewState = "viewState"
            )
        )

        assertEquals(expected, actual)
    }

    companion object {
        val defaultHeaders = mapOf(
            "Cookie" to " cookie_mentorweb_consent_accepted=YES; sessionId",
        )

        val defaultBody = mapOf(
            "javax.faces.partial.ajax" to "true",
            "javax.faces.source" to "loginForm:realizaLogin",
            "javax.faces.partial.execute" to "loginForm:realizaLogin",
            "javax.faces.partial.render" to "loginForm:realizaLogin",
            "loginForm:realizaLogin" to "loginForm:realizaLogin",
            "tipoLogin" to "PADRAO",
            "login" to "11111111111",
            "senha" to "password",
            "mantemConectado" to "S",
            "loginForm" to "loginForm",
            "javax.faces.ViewState" to "viewState",
        )
    }

}