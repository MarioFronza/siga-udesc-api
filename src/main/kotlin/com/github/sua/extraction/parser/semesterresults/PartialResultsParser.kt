package com.github.sua.extraction.parser.semesterresults

import com.github.sua.extraction.exception.ParserException
import org.jsoup.Jsoup

class PartialResultsParser {

    fun extractSemesterResultsUrl(responseContent: String): String {
        val document = Jsoup.parse(responseContent)
        val input = document.getElementById("formPrincipal:linkAbre")
        return input?.attr("value")?.substringAfter("/") ?: throw ParserException("Invalid document key")
    }

    fun extractPeriodsIdentified(responseContent: String): Map<String, String> {
        val document = Jsoup.parse(responseContent)
        val periodsSelect = document.getElementById("lPeriodoLetivo")
        val periods = periodsSelect?.getElementsByAttribute("value")
        val periodMap = mutableMapOf<String, String>()
        periods?.map {
            periodMap[it.text()] = it.attr("value")
        }
        return periodMap.filter { it.key != "Extensão" }
    }

}