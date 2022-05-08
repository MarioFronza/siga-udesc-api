package com.github.sua.usecase.extraction

import com.github.sua.usecase.dto.output.extraction.StudentInfoOutput
import com.github.sua.usecase.dto.input.extraction.StudentInfoInput


interface StudentInfoExtraction {

    fun extract(input: StudentInfoInput): StudentInfoOutput

}