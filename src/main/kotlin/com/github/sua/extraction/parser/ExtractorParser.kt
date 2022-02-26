package com.github.sua.extraction.parser

import com.github.sua.extraction.exception.ParserException
import org.jsoup.Jsoup

class ExtractorParser {

    fun parseViewState(responseContent: String): String {
        val document = Jsoup.parse(responseContent)
        val input = document.getElementById(VIEW_STATE_ELEMENT_ID)
            ?: throw ParserException("Invalid document id: $VIEW_STATE_ELEMENT_ID")
        return input.attr("value")
    }

    fun parseEndpoint(responseContent: String): String {
        val document = Jsoup.parse(responseContent)
        val redirect = document.getElementsByTag("redirect")
        val url = redirect.attr("url")
        return url.substringAfter(BASE_URL)
    }

    companion object {
        const val VIEW_STATE_ELEMENT_ID = "j_id1:javax.faces.ViewState:0"
        const val BASE_URL = "https://siga.udesc.br/"
    }

}