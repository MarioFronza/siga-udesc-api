package com.github.sua.extraction.step.dto


data class SemesterResultsStepInput(
    val endpoint: String,
    val headerSessionId: String,
    val etts: String
)

data class DashboardSemesterResultsStepInput(
    val endpoint: String,
    val headerSessionId: String,
    val etts: String
)

data class SemesterResultsByPeriodStepInput(
    val headerSessionId: String,
    val etts: String,
    val periodIdentified: String
)