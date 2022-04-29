package com.github.sua.usecase.extraction

import com.github.sua.extraction.extractor.results.partial.dto.StudentPartialResults
import com.github.sua.usecase.dto.input.PartialResultsInput


interface PartialResultsExtraction {

    fun extract(input: PartialResultsInput): StudentPartialResults

}