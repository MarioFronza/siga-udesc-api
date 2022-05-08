package com.github.sua.usecase.dto.output.extraction

import kotlinx.serialization.*

@Serializable
data class StudentSemesterOutput(
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