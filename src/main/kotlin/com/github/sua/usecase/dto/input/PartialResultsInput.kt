package com.github.sua.usecase.dto.input

import com.github.sua.entity.student.SigaCredential

data class PartialResultsInput(
    val sigaCredential: SigaCredential,
    val period: PeriodInput,
    val course: String,
    val subject: String,
)