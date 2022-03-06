package com.github.sua.extraction.parser.semesterresults

import com.github.sua.usecase.dto.output.PeriodicalResultsOutput
import com.github.sua.usecase.dto.output.SemesterResultOutput
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

class SemesterResultsParser {

    fun extractSemesterResults(responseContent: String, periodIdentified: String): SemesterResultsResponse {
        val document = Jsoup.parse(responseContent)
        val termSelect = document.getElementById("lPeriodoLetivo")!!
        val courseSelect = document.getElementById("lCurso")!!
        val resultSpan = document.getElementById("resultado")!!

        return SemesterResultsResponse(
            period = extractPeriod(termSelect, periodIdentified),
            course = extractCourse(courseSelect),
            semesterResults = extractSemesterResults(resultSpan)
        )
    }

    private fun extractPeriod(termSelect: Element, periodIdentified: String): String {
        val firstTerm = termSelect.getElementsByAttributeValue("value", periodIdentified)
        return firstTerm.text()
    }

    private fun extractCourse(courseSelect: Element): String {
        val secondItem = courseSelect.child(1)
        return secondItem.text()
    }

    private fun extractSemesterResults(resultSpan: Element): MutableList<SemesterResult> {
        val table = resultSpan.select("center > table.delimitador > tbody").first()!!
        val semesterResults = mutableListOf<SemesterResult>()
        table.children().mapIndexed { index, row ->
            if (row.childrenSize() == 10 && index != 0) {
                semesterResults.add(extractSemesterResultsData(row))
            }
        }
        return semesterResults
    }

    private fun extractSemesterResultsData(subjectRow: Element): SemesterResult {
        val subjectName = subjectRow.child(0).text()
        val groupName = subjectRow.child(1).text()
        val finalGrade = try {
            subjectRow.child(2).text().replace(",", ".").toDouble()
        } catch (e: Exception) {
            0.0
        }
        val courseLoad = subjectRow.child(5).text().toInt()
        val absencesCount = try {
            subjectRow.child(7).text().toInt()
        } catch (e: Exception) {
            0
        }
        val attendancePercentage = try {
            subjectRow.child(8).text().replace(",", ".").toDouble()
        } catch (e: Exception) {
            0.0
        }
        val result = subjectRow.child(9).text()
        return SemesterResult(
            subjectName = subjectName,
            groupName = groupName,
            finalGrade = finalGrade,
            courseLoad = courseLoad,
            absencesCount = absencesCount,
            attendancePercentage = attendancePercentage,
            result
        )
    }

    data class SemesterResultsResponse(
        val period: String,
        val semesterResults: List<SemesterResult>,
        val course: String
    ) {
        fun toSemesterResultsPeriod() = PeriodicalResultsOutput(
            period = period,
            semesterResults = semesterResults.map { it.toSemesterResultOutput() }
        )
    }

    data class SemesterResult(
        val subjectName: String,
        val groupName: String,
        val finalGrade: Double,
        val courseLoad: Int,
        val absencesCount: Int,
        val attendancePercentage: Double,
        val result: String,
    ) {
        fun toSemesterResultOutput() = SemesterResultOutput(
            subjectName = subjectName,
            groupName = groupName,
            finalGrade = finalGrade,
            courseLoad = courseLoad,
            absencesCount = absencesCount,
            attendancePercentage = attendancePercentage,
            result = result
        )
    }

}

