package com.github.sua.usecase.extraction

import com.github.sua.usecase.dto.output.extraction.StudentPartialOutput
import com.github.sua.usecase.dto.input.extraction.PartialResultsInput


interface PartialResultsExtraction {

    fun extract(input: PartialResultsInput): StudentPartialOutput

}