package com.github.sua.extraction.extractor.semesterresults

import com.github.sua.extraction.exception.ExtractorException
import com.github.sua.extraction.extractor.dto.DefaultExtractorParams
import com.github.sua.extraction.extractor.semesterresults.dto.SemesterResultExtractorParams
import com.github.sua.extraction.extractor.semesterresults.dto.SemesterResultPeriod
import com.github.sua.extraction.parser.semesterresults.SemesterResultsParser
import com.github.sua.extraction.step.StepResponse.StepSuccess
import com.github.sua.extraction.step.semesterresults.SemesterResultsByPeriodStep

class SemesterResultsExtractor(
    private val semesterResultsByPeriodStep: SemesterResultsByPeriodStep,
    private val semesterResultsParser: SemesterResultsParser
) {

    fun extract(
        params: DefaultExtractorParams,
        semesterResultExtractorParams: SemesterResultExtractorParams
    ): SemesterResultPeriod {
        return when (val response = semesterResultsByPeriodStep.doRequest(params, semesterResultExtractorParams)) {
            is StepSuccess -> semesterResultsParser.extractSemesterResultsInfo(
                response.payload,
                semesterResultExtractorParams.periodIdentified
            ).toSemesterResultsPeriod()
            else -> throw ExtractorException("Semester results extractor unexpected error")
        }
    }

}