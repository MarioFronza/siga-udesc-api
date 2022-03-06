package com.github.sua.extraction.extractor.semesterresults

import com.github.sua.extraction.extractor.dto.DefaultExtractorParams
import com.github.sua.extraction.extractor.semesterresults.dto.SemesterResultsExtractorResponse
import com.github.sua.extraction.parser.semesterresults.SemesterResultsParser
import com.github.sua.extraction.step.semesterresults.SemesterResultsByPeriodStep

class SemesterResultsExtractor(
    private val semesterResultsByPeriodStep: SemesterResultsByPeriodStep,
    private val semesterResultsParser: SemesterResultsParser
) {

    fun extract(params: DefaultExtractorParams): SemesterResultsExtractorResponse {
        return SemesterResultsExtractorResponse()
    }

}