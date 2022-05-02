package com.github.sua.extraction.extractor.results.partial.dto

import kotlinx.serialization.*

@Serializable
data class StudentPartialResults(
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