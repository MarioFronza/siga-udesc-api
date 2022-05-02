package com.github.sua.extraction.parser.semesterresults

import com.github.sua.extraction.exception.ParserException
import com.github.sua.extraction.extractor.results.partial.dto.PartialResult
import com.github.sua.extraction.extractor.results.partial.dto.PartialResults
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

class PartialResultsParser {

    fun extractPartialResults(
        responseContent: String,
        periodIdentified: String,
        courseIdentified: String,
        subjectIdentified: String,
    ): PartialResults {
        val document = Jsoup.parse(responseContent)
        val termSelect = document.getElementById("lPeriodoLetivo")!!
        val courseSelect = document.getElementById("lCurso")!!
        val resultSpan = document.getElementById("resultado")!!

        return PartialResults(
            course = extractCourse(courseSelect, courseIdentified),
            period = extractPeriod(termSelect, periodIdentified),
            subjectName = "todo",
            results = extractPartialResultsBy(resultSpan),
        )
    }

    private fun extractPeriod(termSelect: Element, periodIdentified: String): String {
        val firstTerm = termSelect.getElementsByAttributeValue("value", periodIdentified)
        return firstTerm.text()
    }

    private fun extractSubject(termSelect: Element, periodIdentified: String): String {
        val firstTerm = termSelect.getElementsByAttributeValue("value", periodIdentified)
        return firstTerm.text()
    }

    private fun extractCourse(termSelect: Element, courseIdentified: String): String {
        val firstTerm = termSelect.getElementsByAttributeValue("value", courseIdentified)
        return firstTerm.text()
    }

    private fun extractPartialResultsBy(resultSpan: Element): MutableList<PartialResult> {
        val table = resultSpan.select("center > table.delimitador > tbody").first()
        val partialResults = mutableListOf<PartialResult>()
        table?.children()?.mapIndexed { index, row ->
            if (row.childrenSize() == 5 && index != 0 && row.getElementsContainingText("Média Parcial").size == 0) {
                partialResults.add(extractPartialResultsData(row))
            }
        }
        return partialResults
    }

    private fun extractPartialResultsData(subjectRow: Element): PartialResult {
        val code = subjectRow.child(0).text()
        val resultName = subjectRow.child(1).text()
        val date = subjectRow.child(2).text()
        val result = subjectRow.child(3).text().toDouble()
        val classLoad = subjectRow.child(4).text().toDouble()

        return PartialResult(
            code = code,
            resultName = resultName,
            date = date,
            classLoad = classLoad,
            result = result
        )
    }

    fun extractPartialResultsUrl(responseContent: String): String {
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

    fun extractCourses(responseContent: String): Map<String, String> {
        val document = Jsoup.parse(responseContent)
        val courseSelect = document.getElementById("lCurso")
        val courses = courseSelect?.getElementsByAttribute("value")
        return courses?.map {
            it.text() to it.attr("value")
        }?.toMap() ?: throw Exception("Invalid element id")
    }

    fun extractSubjects(responseContent: String): Map<String, String> {
        val document = Jsoup.parse(responseContent)
        val courseSelect = document.getElementById("lDisciplina")
        val courses = courseSelect?.getElementsByAttribute("value")
        return courses?.map {
            it.text() to it.attr("value")
        }?.toMap() ?: throw Exception("Invalid element id")
    }


}