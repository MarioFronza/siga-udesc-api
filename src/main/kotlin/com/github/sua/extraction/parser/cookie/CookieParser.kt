package com.github.sua.extraction.parser.cookie

import com.github.sua.extraction.exception.ParserException
import org.jsoup.Jsoup

class CookieParser {

    fun extractActionUrl(responseContent: String): String {
        val document = Jsoup.parse(responseContent)
        val form = document.getElementById("controleSessao") ?: throw ParserException("Invalid document id")
        return form.attr("action")
    }

}