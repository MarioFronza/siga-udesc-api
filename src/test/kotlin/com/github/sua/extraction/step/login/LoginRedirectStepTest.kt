package com.github.sua.extraction.step.login

import com.github.sua.extraction.extractor.login.LoginRedirectExtractorRequest
import com.github.sua.extraction.misc.httpclient.ConnectorHttpClient
import com.github.sua.extraction.misc.httpclient.ConnectorHttpResponse
import com.github.sua.extraction.step.StepResponse.StepSuccess
import com.github.sua.extraction.step.StepResponse.StepError
import io.mockk.every
import io.mockk.mockk
import java.net.HttpURLConnection
import kotlin.test.Test
import kotlin.test.assertEquals

class LoginRedirectStepTest {

    private val httpClient: ConnectorHttpClient = mockk()
    private val step = LoginRedirectStep(httpClient)

    @Test
    fun `should return StepSuccess when execute successfully`() {
        every {
            httpClient.get(
                endpoint = "endpoint",
                headers = defaultHeaders
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
            LoginRedirectExtractorRequest(
                sessionId = "sessionId",
                endpoint = "endpoint"
            )
        )

        assertEquals(expected, actual)
    }

    @Test
    fun `should return StepError when not execute successfully`() {
        every {
            httpClient.get(
                endpoint = "endpoint",
                headers = defaultHeaders
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
            LoginRedirectExtractorRequest(
                sessionId = "sessionId",
                endpoint = "endpoint",
            )
        )

        assertEquals(expected, actual)
    }

    companion object {
        val defaultHeaders = mapOf(
            "Cookie" to "sessionId",
        )
    }

}

