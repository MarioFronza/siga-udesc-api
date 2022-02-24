package com.github.sua.extraction.parser.cookie

import com.github.sua.extraction.exception.ParserException
import com.github.sua.utils.TestUtils.getFileContent
import org.junit.Assert.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals


class CookieParserTest {

    private val parser = CookieParser()

    @Test
    fun `should extract action url from cookie response`() {
        val cookieResponse = getFileContent("login/cookie_step_response.html")
        val expected = "/sigaSecurityG5/login.jsf;jsessionid=1FBF366F2EDCDB2985BD1207F3A3F53D.ap004"

        val actionUrl = parser.extractAction(cookieResponse)

        assertEquals(expected, actionUrl)
    }

    @Test
    fun `should throw ParserException when does not exist controleSessao element`() {
        val cookieResponse = "invalid response"

        assertThrows(ParserException::class.java) {
            parser.extractAction(cookieResponse)
        }
    }

}