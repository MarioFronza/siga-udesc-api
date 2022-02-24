package com.github.sua.extraction.step.login

import com.github.sua.extraction.dto.StepResponse.StepError
import com.github.sua.extraction.dto.StepResponse.StepSuccess
import com.github.sua.extraction.misc.httpclient.ExtractorHttpClient
import com.github.sua.extraction.misc.httpclient.StepHttpResponse
import io.mockk.every
import io.mockk.mockk
import java.net.HttpURLConnection
import kotlin.test.Test
import kotlin.test.assertEquals


class CookieStepTest {

    private val httpClient: ExtractorHttpClient = mockk()
    private val step = CookieStep(httpClient)

    @Test
    fun `should return StepSuccess when execute successfully`() {
        every {
            httpClient.get("sigaSecurityG5")
        } returns StepHttpResponse(
            responseContentType = null,
            statusCode = HttpURLConnection.HTTP_OK,
            headers = emptyMap(),
            body = "SUCCESS RESPONSE",
            isSuccessful = true
        )

        val expected = StepSuccess(payload = "SUCCESS RESPONSE")
        val actual = step.doRequest()

        assertEquals(expected, actual)
    }

    @Test
    fun `should return StepError when not execute successfully`() {
        every {
            httpClient.get("sigaSecurityG5")
        } returns StepHttpResponse(
            responseContentType = null,
            statusCode = HttpURLConnection.HTTP_BAD_REQUEST,
            headers = emptyMap(),
            body = "ERROR RESPONSE",
            isSuccessful = false
        )

        val expected = StepError(payload = "ERROR RESPONSE")
        val actual = step.doRequest()

        assertEquals(expected, actual)
    }

}