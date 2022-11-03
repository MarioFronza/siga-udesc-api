package com.github.sua.http.controller.dto

import kotlinx.serialization.Serializable

@Serializable
data class StudentSemesterResultsHttpOutput(
    val studentName: String,
    val semesterResults: SemesterResultsHttpOutput,
)

@Serializable
data class SemesterResultsHttpOutput(
    val period: String,
    val course: String,
    val semesterResults: List<SemesterResultHttpOutput>,
)

@Serializable
data class SemesterResultHttpOutput(
    val subjectName: String,
    val groupName: String,
    val finalGrade: Double,
    val courseLoad: Int,
    val absencesCount: Int,
    val attendancePercentage: Double,
    val result: String,
)