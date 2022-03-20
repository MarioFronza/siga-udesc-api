package com.github.sua.extraction.extractor.semesterresults.dto

import kotlinx.serialization.*

@Serializable
data class StudentSemesterResults(
    val studentName: String,
    val semesterResults: SemesterResultsResponse
)

@Serializable
data class SemesterResultsResponse(
    val period: String,
    val semesterResults: List<SemesterResult>,
    val course: String
)

@Serializable
data class SemesterResult(
    val subjectName: String,
    val groupName: String,
    val finalGrade: Double,
    val courseLoad: Int,
    val absencesCount: Int,
    val attendancePercentage: Double,
    val result: String,
)