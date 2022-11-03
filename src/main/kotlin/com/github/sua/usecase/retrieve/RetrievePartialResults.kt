package com.github.sua.usecase.retrieve

import com.github.sua.usecase.dto.input.extraction.PartialResultsInput
import com.github.sua.usecase.dto.output.extraction.StudentPartialOutput
import com.github.sua.usecase.extraction.PartialResultsExtraction

class RetrievePartialResults(
    private val partialResultsExtraction: PartialResultsExtraction
    ) {

    fun retrieve(input: PartialResultsInput): StudentPartialOutput {
        return partialResultsExtraction.extract(input)
    }
}
