package com.github.sua.extraction.extractor.results.partial

import com.github.sua.integration.exception.ExtractorException
import com.github.sua.integration.exception.ParserException
import com.github.sua.integration.siga.extraction.extractor.results.partial.DashboardPartialResultsExtractor
import com.github.sua.integration.siga.extraction.extractor.results.partial.DashboardPartialResultsExtractorRequest
import com.github.sua.integration.siga.extraction.extractor.results.partial.DashboardPartialResultsExtractorResponse
import com.github.sua.integration.siga.step.StepResponse.StepError
import com.github.sua.integration.siga.step.StepResponse.StepSuccess
import com.github.sua.integration.siga.parser.results.PartialResultsParser
import com.github.sua.integration.siga.step.results.partial.DashboardPartialResultsStep
import org.junit.Assert.assertThrows
import io.mockk.every
import io.mockk.mockk
import kotlin.test.assertEquals
import org.junit.Test


class DashboardPartialResultsExtractorTest {

    private val dashboardPartialResultsStep: DashboardPartialResultsStep = mockk()
    private val partialResultsParser: PartialResultsParser = mockk()
    private val dashboardPartialResultsExtractor = DashboardPartialResultsExtractor(
        dashboardPartialResultsStep,
        partialResultsParser
    )

    @Test
    fun `should extract and return a valid output`() {
        val payload = "response_success"
        val semesterResultsUrl = "semesterResultsUrl"

        val request = DashboardPartialResultsExtractorRequest(
            endpoint = "endpoint",
            sessionId = "sessionId",
            etts = "etts"
        )

        every { dashboardPartialResultsStep.doRequest(request) } returns StepSuccess(
            payload = payload,
            headers = emptyMap()
        )
        every { partialResultsParser.extractPartialResultsUrl(payload) } returns semesterResultsUrl

        val expected = DashboardPartialResultsExtractorResponse(
            endpoint = semesterResultsUrl,
        )
        val actual = dashboardPartialResultsExtractor.extract(request)

        assertEquals(expected, actual)
    }

    @Test
    fun `should throw ExtractorException when dashboard partial results step fails`() {
        val payload = "error"
        val request = DashboardPartialResultsExtractorRequest(
            endpoint = "endpoint",
            sessionId = "sessionId",
            etts = "etts"
        )

        every { dashboardPartialResultsStep.doRequest(request) } returns StepError(payload = payload)

        assertThrows(ExtractorException::class.java) {
            dashboardPartialResultsExtractor.extract(request)
        }
    }

    @Test
    fun `should throw ParseException when partial results parser fails`() {
        val payload = "success with invalid format"
        val request = DashboardPartialResultsExtractorRequest(
            endpoint = "endpoint",
            sessionId = "sessionId",
            etts = "etts"
        )

        every { dashboardPartialResultsStep.doRequest(request) } returns StepSuccess(
            payload = payload,
            headers = emptyMap()
        )
        every { partialResultsParser.extractPartialResultsUrl(payload) } throws ParserException("parse exception")

        assertThrows(ParserException::class.java) {
            dashboardPartialResultsExtractor.extract(request)
        }
    }


}