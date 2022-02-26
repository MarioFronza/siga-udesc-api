package com.github.sua.extraction.extractor.cookie

import com.github.sua.extraction.step.StepResponse.StepError
import com.github.sua.extraction.step.StepResponse.StepSuccess
import com.github.sua.extraction.exception.ExtractorException
import com.github.sua.extraction.exception.ParserException
import com.github.sua.extraction.extractor.dto.DefaultExtractorParams
import com.github.sua.extraction.extractor.login.CookieExtractor
import com.github.sua.extraction.parser.cookie.CookieParser
import com.github.sua.extraction.step.login.CookieStep
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
        val payload = "success"
        val actionUrl = "action url"

        every { cookieStep.doRequest() } returns StepSuccess(payload = payload, headers = emptyMap())
        every { cookieParser.extractActionUrl(payload) } returns actionUrl

        val expected = DefaultExtractorParams(endpoint = actionUrl)

        val actual = cookieExtractor.extract()

        assertEquals(expected, actual)
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