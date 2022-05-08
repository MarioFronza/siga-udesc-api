package com.github.sua.usecase.dto.output.extraction

import kotlinx.serialization.*

@Serializable
data class StudentPartialOutput(
    val studentName: String,
    val partialResults: PartialResults
)

@Serializable
data class PartialResults(
    val period: String,
    val course: String,
    val subjectName: String,
    val results: List<PartialResult>
)

@Serializable
data class PartialResult(
    val code: String,
    val resultName: String,
    val date: String,
    val classLoad: Double,
    val result: Double,
)