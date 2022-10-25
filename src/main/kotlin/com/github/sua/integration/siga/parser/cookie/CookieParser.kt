package com.github.sua.integration.siga.parser.cookie

import com.github.sua.integration.exception.ParserException
import org.jsoup.Jsoup

class CookieParser {

    fun extractActionUrl(responseContent: String): String {
        val document = Jsoup.parse(responseContent)
        val form = document.getElementById("controleSessao") ?: throw ParserException("Invalid document id")
        return form.attr("action")
    }

}