package com.github.sua.extraction.step.results.partial

import com.github.sua.integration.siga.extraction.extractor.results.partial.PartialResultsExtractorRequest
import com.github.sua.integration.siga.step.StepResponse.StepError
import com.github.sua.integration.siga.step.StepResponse.StepSuccess
import com.github.sua.integration.http.ConnectorHttpClient
import com.github.sua.integration.http.ConnectorHttpResponse
import com.github.sua.integration.siga.step.results.partial.PartialResultsStep
import io.mockk.every
import io.mockk.mockk
import kotlin.test.assertEquals
import java.net.HttpURLConnection
import kotlin.test.Test


class PartialResultsStepTest {

    private val httpClient: ConnectorHttpClient = mockk()
    private val step = PartialResultsStep(httpClient)

    @Test
    fun `should return StepSuccess when execute successfully`(){
        every {
            httpClient.get(endpoint = "endpoint", headers = defaultHeaders)
        } returns ConnectorHttpResponse(
            responseContentType = null,
            statusCode = HttpURLConnection.HTTP_OK,
            headers = emptyMap(),
            body = "SUCCESS RESPONSE",
            isSuccessful = true
        )

        val expected = StepSuccess(payload = "SUCCESS RESPONSE", headers = emptyMap())
        val actual = step.doRequest(
            PartialResultsExtractorRequest(
                endpoint = "endpoint",
                sessionId = "sessionId",
                etts = "etts"
            )
        )

        assertEquals(expected, actual)
    }

    @Test
    fun `should return StepError when not execute successfully`() {
        every {
            httpClient.get(endpoint = "endpoint", headers = defaultHeaders)
        } returns ConnectorHttpResponse(
            responseContentType = null,
            statusCode = HttpURLConnection.HTTP_BAD_REQUEST,
            headers = emptyMap(),
            body = "ERROR RESPONSE",
            isSuccessful = false
        )

        val expected = StepError(payload = "ERROR RESPONSE")
        val actual = step.doRequest(
            PartialResultsExtractorRequest(
                endpoint = "endpoint",
                sessionId = "sessionId",
                etts = "etts"
            )
        )

        assertEquals(expected, actual)
    }

    companion object {
        val defaultHeaders = mapOf(
            "Cookie" to "sessionId; ECS=S; etts",
        )
    }

}