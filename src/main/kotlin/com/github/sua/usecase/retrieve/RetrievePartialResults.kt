package com.github.sua.usecase.retrieve

import com.github.sua.extraction.extractor.results.partial.dto.StudentPartialResults
import com.github.sua.extraction.extractor.results.semester.dto.StudentSemesterResults
import com.github.sua.usecase.dto.input.PartialResultsInput
import com.github.sua.usecase.dto.input.SemesterResultsInput
import com.github.sua.usecase.extraction.PartialResultsExtraction
import com.github.sua.usecase.extraction.SemesterResultsExtraction

class RetrievePartialResults(
    private val partialResultsExtraction: PartialResultsExtraction
) {

    fun retrieve(input: PartialResultsInput): StudentPartialResults {
        return partialResultsExtraction.extract(input)
    }

}