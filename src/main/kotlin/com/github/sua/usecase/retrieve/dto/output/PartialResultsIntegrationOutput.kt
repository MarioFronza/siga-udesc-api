package com.github.sua.usecase.retrieve.dto.output

import kotlinx.serialization.Serializable

@Serializable
data class StudentPartialResultsIntegrationOutput(
    val studentName: String,
    val partialResults: PartialResultsIntegrationOutput
)

@Serializable
data class PartialResultsIntegrationOutput(
    val period: String,
    val course: String,
    val subjectName: String,
    val results: List<PartialResultIntegrationOutput>
)
@Serializable
data class PartialResultIntegrationOutput(
    val code: String,
    val evaluationName: String,
    val date: String,
    val classLoad: Double,
    val finalGrade: Double,
){
    fun toStudentInfoPartialResult() = StudentInfoPartialResult(
        code = code ,
        evaluationName = evaluationName,
        date = date,
        classLoad= classLoad,
        finalGrade = finalGrade
    )
}