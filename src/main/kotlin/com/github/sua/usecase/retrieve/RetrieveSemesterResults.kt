package com.github.sua.usecase.retrieve

import com.github.sua.usecase.dto.output.extraction.StudentSemesterOutput
import com.github.sua.usecase.dto.input.extraction.SemesterResultsInput
import com.github.sua.usecase.extraction.SemesterResultsExtraction

class RetrieveSemesterResults(
    private val semesterResultsExtraction: SemesterResultsExtraction
) {

    fun retrieve(input: SemesterResultsInput): StudentSemesterOutput {
        return semesterResultsExtraction.extract(input)
    }

}