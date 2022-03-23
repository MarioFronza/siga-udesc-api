package com.github.sua.extraction.extractor.cookie

import com.github.sua.extraction.step.StepResponse.StepError
import com.github.sua.extraction.step.StepResponse.StepSuccess
import com.github.sua.extraction.exception.ExtractorException
import com.github.sua.extraction.exception.ParserException
import com.github.sua.extraction.extractor.login.CookieExtractor
import com.github.sua.extraction.extractor.login.CookieExtractorResponse
import com.github.sua.extraction.parser.cookie.CookieParser
import com.github.sua.extraction.step.login.CookieStep
import com.github.sua.utils.TestUtils.getFileContent
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals


class CookieExtractorTest {

    private val cookieStep: CookieStep = mockk()
    private val cookieParser: CookieParser = mockk()
    private val cookieExtractor = CookieExtractor(cookieStep, cookieParser)

    @Test
    fun `should extract and return a valid output`() {
        val payload = getFileContent("login/cookie_step_response.html")
        val actionUrl = "actionUrl"
        val responseHeaders = mapOf(
            "Set-Cookie" to listOf("etts", "sessionId")
        )

        every { cookieStep.doRequest() } returns StepSuccess(payload = payload, headers = responseHeaders)
        every { cookieParser.extractActionUrl(payload) } returns actionUrl

        val expected = CookieExtractorResponse(
            sessionId = "sessionId",
            endpoint = "actionUrl",
            viewState = "7044885052596479123:-1015631438468938764"
        )
        val actual = cookieExtractor.extract()

        assertEquals(expected, actual)
    }

    @Test
    fun `should throw ParserException when cookie step return a invalid view state`() {
        val payload = "invalid view state"
        val actionUrl = "actionUrl"

        every { cookieStep.doRequest() } returns StepSuccess(payload = payload, headers = emptyMap())
        every { cookieParser.extractActionUrl(payload) } returns actionUrl

        assertThrows(ParserException::class.java) {
            cookieExtractor.extract()
        }
    }

    @Test
    fun `should throw IllegalArgumentException when cookie step return a invalid cookie response`() {
        val payload = getFileContent("login/cookie_step_response.html")
        val actionUrl = "actionUrl"

        every { cookieStep.doRequest() } returns StepSuccess(payload = payload, headers = emptyMap())
        every { cookieParser.extractActionUrl(payload) } returns actionUrl

        assertThrows(IllegalArgumentException::class.java) {
            cookieExtractor.extract()
        }
    }

    @Test
    fun `should throw ExtractorException when cookie step fails`() {
        val payload = "error"

        every { cookieStep.doRequest() } returns StepError(payload = payload)

        assertThrows(ExtractorException::class.java) {
            cookieExtractor.extract()
        }
    }

    @Test
    fun `should throw ParseException when cookie parser fails`() {
        val payload = "success with invalid format"

        every { cookieStep.doRequest() } returns StepSuccess(payload = payload, headers = emptyMap())
        every { cookieParser.extractActionUrl(payload) } throws ParserException("parse exception")

        assertThrows(ParserException::class.java) {
            cookieExtractor.extract()
        }
    }

}