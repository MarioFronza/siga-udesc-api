package com.github.sua.extraction.extractor.semesterresults.dto


data class SemesterResultPeriod(
    val semesterResults: List<SemesterResult>,
    val period: String,
)

data class SemesterResult(
    val subjectName: String,
    val groupName: String,
    val finalGrade: Double,
    val courseLoad: Int,
    val absencesCount: Int,
    val attendancePercentage: Double,
    val result: String,
)