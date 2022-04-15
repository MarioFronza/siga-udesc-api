package com.github.sua.extraction.extractor.results.semester

import com.github.sua.extraction.exception.ExtractorException
import com.github.sua.extraction.parser.semesterresults.SemesterResultsParser
import com.github.sua.extraction.step.StepResponse.StepSuccess
import com.github.sua.extraction.step.results.semester.SemesterResultsStep

class SemesterResultsExtractor(
    private val semesterResultStep: SemesterResultsStep,
    private val semesterResultsParser: SemesterResultsParser
) {

    fun extract(request: SemesterResultsExtractorRequest): SemesterResultsExtractorResponse {
        return when (val response = semesterResultStep.doRequest(request)) {
            is StepSuccess -> SemesterResultsExtractorResponse(
                sessionId = response.getSessionId(position = 0),
                periodIdentified = semesterResultsParser.extractPeriods(response.payload)
            )
            else -> throw ExtractorException("Semester results extractor unexpected error")
        }
    }

}

data class SemesterResultsExtractorRequest(
    val endpoint: String,
    val sessionId: String,
    val etts: String,
)

data class SemesterResultsExtractorResponse(
    val sessionId: String,
    val periodIdentified: Map<String, String>,
)