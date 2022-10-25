package com.github.sua.usecase.integration.dto.results

import kotlinx.serialization.Serializable

@Serializable
data class StudentSemesterResultsIntegrationOutput(
    val studentName: String,
    val semesterResults: SemesterResultsIntegrationOutput,
)


@Serializable
data class SemesterResultsIntegrationOutput(
    val period: String,
    val course: String,
    val semesterResults: List<SemesterResultIntegrationOutput>,
)

@Serializable
data class SemesterResultIntegrationOutput(
    val subjectName: String,
    val groupName: String,
    val finalGrade: Double,
    val courseLoad: Int,
    val absencesCount: Int,
    val attendancePercentage: Double,
    val result: String,
)