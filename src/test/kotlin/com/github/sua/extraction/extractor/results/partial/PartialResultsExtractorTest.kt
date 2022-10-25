package com.github.sua.extraction.extractor.results.partial

import com.github.sua.integration.exception.ExtractorException
import com.github.sua.integration.exception.ParserException
import com.github.sua.integration.siga.extraction.extractor.results.partial.PartialResultsExtractor
import com.github.sua.integration.siga.extraction.extractor.results.partial.PartialResultsExtractorRequest
import com.github.sua.integration.siga.extraction.extractor.results.partial.PartialResultsExtractorResponse
import com.github.sua.integration.siga.parser.results.PartialResultsParser
import com.github.sua.integration.siga.step.StepResponse
import com.github.sua.integration.siga.step.results.partial.PartialResultsStep
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test
import kotlin.test.assertEquals

class PartialResultsExtractorTest {

    private val partialResultsStep: PartialResultsStep = mockk()
    private val partialResultsParser: PartialResultsParser = mockk()
    private val partialResultsExtractor = PartialResultsExtractor(
        partialResultsStep,
        partialResultsParser
    )

    @Test
    fun `should extract and return a valid output`() {
        val payload = "response_success"
        val sessionId = "sessionId"

        val request = PartialResultsExtractorRequest(
            endpoint = "endpoint",
            sessionId = "sessionId",
            etts = "etts"
        )

        every { partialResultsStep.doRequest(request) } returns StepResponse.StepSuccess(
            payload = payload,
            headers = emptyMap()
        )
        every { partialResultsParser.extractPartialResultsUrl(payload) } returns sessionId
        every { partialResultsParser.extractPeriodsIdentified(payload) } returns emptyMap()

        val expected = PartialResultsExtractorResponse(
            sessionId = sessionId,
            periodsIdentified = emptyMap(),
            coursesIdentified = partialResultsParser.extractCourses(response.payload),
            subjectsIdentified = partialResultsParser.extractSubjects(response.payload)
        )
        val actual = partialResultsExtractor.extract(request)

        assertEquals(expected, actual)
    }

    @Test
    fun `should throw ExtractorException when partial results step fails`() {
        val payload = "error"
        val request = PartialResultsExtractorRequest(
            endpoint = "endpoint",
            sessionId = "sessionId",
            etts = "etts"
        )

        every { partialResultsStep.doRequest(request) } returns StepResponse.StepError(payload = payload)

        Assert.assertThrows(ExtractorException::class.java) {
            partialResultsExtractor.extract(request)
        }
    }

    @Test
    fun `should throw ParseException when partial results parser fails`() {
        val payload = "success with invalid format"
        val request = PartialResultsExtractorRequest(
            endpoint = "endpoint",
            sessionId = "sessionId",
            etts = "etts"
        )

        every { partialResultsStep.doRequest(request) } returns StepResponse.StepSuccess(
            payload = payload,
            headers = emptyMap()
        )
        every { partialResultsParser.extractPartialResultsUrl(payload) } throws ParserException("parse exception")

        Assert.assertThrows(ParserException::class.java) {
            partialResultsExtractor.extract(request)
        }
    }

}