package com.github.sua.extraction.parser.dashboard

import com.github.sua.extraction.exception.ParserException
import org.jsoup.Jsoup

class DashboardParser {

    fun extractDashboardActionPageUrl(responseContent: String, resultPageType:String): String {
        val document = Jsoup.parse(responseContent)
        val elementId = getElementIdByPageType(resultPageType)
        val li = document.getElementById(elementId) ?: throw ParserException("Invalid document id")
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

    private fun getElementIdByPageType(pageType: String)= when(pageType){
        SEMESTER_RESULT_PAGE -> "formMenu:menu_3_3"
        PARTIAL_RESULT_PAGE -> "formMenu:menu_3_7"
        else -> throw ParserException("Invalid page type $pageType")
    }

    companion object {
        const val SEMESTER_RESULT_PAGE = "semester_result_page"
        const val PARTIAL_RESULT_PAGE = "partial_result_page"
    }

}