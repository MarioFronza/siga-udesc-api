package com.github.sua.extraction.parser.dashboard

import com.github.sua.extraction.exception.ParserException
import org.jsoup.Jsoup

class DashboardParser {

    fun extractDashboardSemesterResultsUrl(responseContent: String): String {
        val document = Jsoup.parse(responseContent)
        val li = document.getElementById("formMenu:menu_3_3") ?: throw ParserException("Invalid document id")
        val a = li.children()
        return a.attr("href").substringAfter("/")
    }

    fun extractSemesterResultsUrl(responseContent: String): String {
        val document = Jsoup.parse(responseContent)
        val input = document.getElementById("formPrincipal:linkAbre") ?: throw ParserException("Invalid document id")
        return input.attr("value")
    }

    fun extractStudentName(responseContent: String): String {
        val document = Jsoup.parse(responseContent)
        val label = document.getElementById("formMenu:j_idt111") ?: throw ParserException("Invalid document id")
        return label.text()
    }

}