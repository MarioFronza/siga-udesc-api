package com.github.sua.extraction.extractor.semesterresults

import com.github.sua.extraction.exception.ExtractorException
import com.github.sua.extraction.parser.semesterresults.SemesterResultsParser
import com.github.sua.extraction.step.StepResponse.StepSuccess
import com.github.sua.extraction.step.semesterresults.SemesterResultsByPeriodStep

class SemesterResultsByPeriodExtractor(
    private val semesterResultsByPeriodStep: SemesterResultsByPeriodStep,
    private val semesterResultsParser: SemesterResultsParser
) {

    fun extract(request: SemesterResultsByPeriodExtractorRequest): SemesterResultsByPeriodExtractorResponse {
        return when (val response = semesterResultsByPeriodStep.doRequest(request)) {
            is StepSuccess -> SemesterResultsByPeriodExtractorResponse(
                semesterResults = semesterResultsParser.extractSemesterResultsInfo(
                    response.payload,
                    request.periodIdentified
                )
            )
            else -> throw ExtractorException("Semester results extractor unexpected error")
        }
    }

}

data class SemesterResultsByPeriodExtractorRequest(
    val sessionId: String,
    val etts: String,
    val periodIdentified: String,
)

data class SemesterResultsByPeriodExtractorResponse(
    val semesterResults: SemesterResultsParser.SemesterResultsOutput,
)