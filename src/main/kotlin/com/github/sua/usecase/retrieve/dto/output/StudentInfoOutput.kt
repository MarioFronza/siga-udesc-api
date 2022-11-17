package com.github.sua.usecase.retrieve.dto.output

import kotlinx.serialization.Serializable


@Serializable
data class StudentInfoOutput(
    val studentName: String,
    val semesterResults: List<StudentInfoSemesterResults>
)

@Serializable
data class StudentInfoSemesterResults(
    val period: String,
    val course: String,
    val semesterResults: List<StudentInfoSemesterResult>
)

@Serializable
data class StudentInfoSemesterResult(
    val subjectName: String,
    val groupName: String,
    val finalGrade: Double,
    val courseLoad: Int,
    val absencesCount: Int,
    val attendancePercentage: Double,
    val result: String,
    val partialResults: List<StudentInfoPartialResult>
)

@Serializable
data class StudentInfoPartialResult(
    val code: String,
    val evaluationName: String,
    val date: String,
    val classLoad: Double,
    val finalGrade: Double,
)