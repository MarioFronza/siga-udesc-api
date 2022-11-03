package com.github.sua.usecase.retrieve.dto

data class StudentSemesterResultsIntegrationOutput(
    val studentName: String,
    val semesterResults: SemesterResultsIntegrationOutput,
)


data class SemesterResultsIntegrationOutput(
    val period: String,
    val course: String,
    val semesterResults: List<SemesterResultIntegrationOutput>,
)

data class SemesterResultIntegrationOutput(
    val subjectName: String,
    val groupName: String,
    val finalGrade: Double,
    val courseLoad: Int,
    val absencesCount: Int,
    val attendancePercentage: Double,
    val result: String,
)