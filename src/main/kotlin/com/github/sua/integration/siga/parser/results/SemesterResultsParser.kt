package com.github.sua.integration.siga.parser.results

import com.github.sua.integration.siga.extraction.extractor.results.partial.PartialResultsByPeriodExtractorResponse
import com.github.sua.usecase.retrieve.dto.output.SemesterResultIntegrationOutput
import com.github.sua.usecase.retrieve.dto.output.SemesterResultsIntegrationOutput
import com.github.sua.usecase.retrieve.dto.output.StudentInfoSemesterResult
import com.github.sua.usecase.retrieve.dto.output.StudentInfoSemesterResults
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

class SemesterResultsParser {

    fun extractSemesterResultsInfo(responseContent: String, periodIdentified: String): SemesterResultsOutput {
        val document = Jsoup.parse(responseContent)
        val termSelect = document.getElementById("lPeriodoLetivo")!!
        val courseSelect = document.getElementById("lCurso")!!
        val resultSpan = document.getElementById("resultado")!!

        return SemesterResultsOutput(
            period = extractPeriod(termSelect, periodIdentified),
            course = extractCourse(courseSelect),
            semesterResults = extractSemesterResults(resultSpan)
        )
    }

    fun extractPeriods(responseContent: String): Map<String, String> {
        val document = Jsoup.parse(responseContent)
        val periodsSelect = document.getElementById("lPeriodoLetivo")
        val periods = periodsSelect?.getElementsByAttribute("value")
        val periodMap = mutableMapOf<String, String>()
        periods?.map {
            periodMap[it.text()] = it.attr("value")
        }
        return periodMap.filter { it.key != "Extensão" }
    }

    fun extractSemesterResultsUrl(responseContent: String): String {
        val document = Jsoup.parse(responseContent)
        val input = document.getElementById("formPrincipal:linkAbre")
        return input?.attr("value")?.substringAfter("/") ?: throw Exception("Invalid document key")
    }

    private fun extractPeriod(termSelect: Element, periodIdentified: String): String {
        val firstTerm = termSelect.getElementsByAttributeValue("value", periodIdentified)
        return firstTerm.text()
    }

    private fun extractCourse(courseSelect: Element): String {
        val secondItem = courseSelect.child(1)
        return secondItem.text()
    }

    private fun extractSemesterResults(resultSpan: Element): List<SemesterResultOutput> {
        val table = resultSpan.select("center > table.delimitador > tbody").first()!!
        val semesterResults = mutableListOf<SemesterResultOutput>()
        table.children().mapIndexed { index, row ->
            if (row.childrenSize() == 10 && index != 0) {
                semesterResults.add(extractSemesterResultsData(row))
            }
        }
        return semesterResults
    }

    private fun extractSemesterResultsData(subjectRow: Element): SemesterResultOutput {
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
        return SemesterResultOutput(
            subjectName = subjectName,
            groupName = groupName,
            finalGrade = finalGrade,
            courseLoad = courseLoad,
            absencesCount = absencesCount,
            attendancePercentage = attendancePercentage,
            result
        )
    }

    data class SemesterResultsOutput(
        val period: String,
        val course: String,
        val semesterResults: List<SemesterResultOutput>
    ) {
        fun toSemesterResultsPeriod() = SemesterResultsIntegrationOutput(
            period = period,
            course = course,
            semesterResults = semesterResults.map { it.toSemesterResult() }
        )

        fun toStudentInfoSemesterResults(semesterResults: List<StudentInfoSemesterResult>) =
            StudentInfoSemesterResults(
                period = period,
                course = course,
                semesterResults = semesterResults
            )
    }

    data class SemesterResultOutput(
        val subjectName: String,
        val groupName: String,
        val finalGrade: Double,
        val courseLoad: Int,
        val absencesCount: Int,
        val attendancePercentage: Double,
        val result: String,
    ) {
        fun toSemesterResult() = SemesterResultIntegrationOutput(
            subjectName = subjectName,
            groupName = groupName,
            finalGrade = finalGrade,
            courseLoad = courseLoad,
            absencesCount = absencesCount,
            attendancePercentage = attendancePercentage,
            result = result
        )

        fun toStudentInfoSemesterResult(partialResults: List<PartialResultsByPeriodExtractorResponse>) =
            StudentInfoSemesterResult(
                subjectName = subjectName,
                groupName = groupName,
                finalGrade = finalGrade,
                courseLoad = courseLoad,
                absencesCount = absencesCount,
                attendancePercentage = attendancePercentage,
                result = result,
                partialResults = partialResults.flatMap {
                    it.partialResults.results.map { result ->
                        result.toStudentInfoPartialResult()
                    }
                }
            )
    }

}

