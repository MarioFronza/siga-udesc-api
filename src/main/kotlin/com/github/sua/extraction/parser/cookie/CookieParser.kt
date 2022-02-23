package com.github.sua.extraction.parser.cookie

import org.jsoup.Jsoup

class CookieParser {

    fun extractAction(responseContent: String): String {
        val document = Jsoup.parse(responseContent)
        val form = document.getElementById("controleSessao") ?: throw Exception("Invalid document id")
        return form.attr("action")
    }

}