package com.github.sua.usecase.dto.input

import com.github.sua.entity.student.SigaCredential

data class SemesterResultsInput(
    val sigaCredential: SigaCredential,
    val period: PeriodInput
)