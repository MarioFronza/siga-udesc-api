package com.github.sua.usecase.retrieve

import com.github.sua.extraction.extractor.semesterresults.dto.SemesterResultPeriod
import com.github.sua.usecase.dto.input.SemesterResultsInput
import com.github.sua.usecase.extraction.SemesterResultsExtraction

class RetrieveSemesterResults(
    private val semesterResultsExtraction: SemesterResultsExtraction
) {

    fun retrieve(input: SemesterResultsInput): SemesterResultPeriod {
        return semesterResultsExtraction.extract(input)
    }

}