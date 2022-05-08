package com.github.sua.usecase.extraction

import com.github.sua.usecase.dto.output.extraction.StudentSemesterOutput
import com.github.sua.usecase.dto.input.extraction.SemesterResultsInput


interface SemesterResultsExtraction {

    fun extract(input: SemesterResultsInput): StudentSemesterOutput

}