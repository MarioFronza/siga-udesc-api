package com.github.sua.integration.siga.extraction.extractor.results.semester

import com.github.sua.integration.exception.ExtractorException
import com.github.sua.integration.siga.parser.results.SemesterResultsParser
import com.github.sua.integration.siga.step.StepResponse.StepSuccess
import com.github.sua.integration.siga.step.results.semester.SemesterResultsByPeriodStep

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