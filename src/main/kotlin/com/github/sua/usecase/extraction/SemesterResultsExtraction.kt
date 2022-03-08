package com.github.sua.usecase.extraction

import com.github.sua.extraction.extractor.semesterresults.dto.SemesterResultPeriod
import com.github.sua.usecase.dto.input.SemesterResultsInput


interface SemesterResultsExtraction {

    fun extract(input: SemesterResultsInput): SemesterResultPeriod

}