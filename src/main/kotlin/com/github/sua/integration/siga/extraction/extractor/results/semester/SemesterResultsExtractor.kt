package com.github.sua.integration.siga.extraction.extractor.results.semester

import com.github.sua.integration.exception.ExtractorException
import com.github.sua.integration.siga.parser.results.SemesterResultsParser
import com.github.sua.integration.siga.step.StepResponse.StepSuccess
import com.github.sua.integration.siga.step.results.semester.SemesterResultsStep

class SemesterResultsExtractor(
    private val semesterResultStep: SemesterResultsStep,
    private val semesterResultsParser: SemesterResultsParser
) {

    fun extract(request: SemesterResultsExtractorRequest): SemesterResultsExtractorResponse {
        return when (val response = semesterResultStep.doRequest(request)) {
            is StepSuccess -> SemesterResultsExtractorResponse(
                sessionId = response.getSessionId(position = 0),
                periodsIdentified = semesterResultsParser.extractPeriods(response.payload)
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
    val periodsIdentified: Map<String, String>,
) {
    fun toSemesterResultsByPeriodExtractorRequest(etts: String, periodIdentified: String) =
        SemesterResultsByPeriodExtractorRequest(
            sessionId = sessionId,
            etts = etts,
            periodIdentified = periodIdentified
        )
}