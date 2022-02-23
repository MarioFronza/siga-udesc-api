package com.github.sua.usecase.dto.output

class SemesterResultsOutput(
    val studentName: String,
    val course: String,
    val periodicalResults: List<PeriodicalResultsOutput>
)

data class PeriodicalResultsOutput(
    val semesterResults: List<SemesterResultOutput>,
    val period: String,
)

data class SemesterResultOutput(
    val subjectName: String,
    val groupName: String,
    val finalGrade: Double,
    val courseLoad: Int,
    val absencesCount: Int,
    val attendancePercentage: Double,
    val result: String,
)