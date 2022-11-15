package com.github.sua.integration.siga.extraction.extractor.results.partial

import com.github.sua.integration.exception.ExtractorException
import com.github.sua.integration.siga.extraction.extractor.results.semester.SemesterResultsByPeriodExtractorRequest
import com.github.sua.integration.siga.parser.results.PartialResultsParser
import com.github.sua.integration.siga.step.StepResponse.StepSuccess
import com.github.sua.integration.siga.step.results.partial.PartialResultsStep

class PartialResultsExtractor(
    private val partialResultsStep: PartialResultsStep,
    private val partialResultsParser: PartialResultsParser
) {

    fun extract(request: PartialResultsExtractorRequest): PartialResultsExtractorResponse {
        return when (val response = partialResultsStep.doRequest(request)) {
            is StepSuccess -> PartialResultsExtractorResponse(
                sessionId = response.getSessionId(position = 0),
                periodsIdentified = partialResultsParser.extractPeriodsIdentified(response.payload),
                coursesIdentified = partialResultsParser.extractCourses(response.payload),
                subjectsIdentified = partialResultsParser.extractSubjects(response.payload)
            )

            else -> throw ExtractorException("Partial results extractor unexpected error")
        }
    }

}

data class PartialResultsExtractorRequest(
    val endpoint: String,
    val sessionId: String,
    val etts: String,
)

data class PartialResultsExtractorResponse(
    val sessionId: String,
    val periodsIdentified: Map<String, String>,
    val coursesIdentified: Map<String, String>,
    val subjectsIdentified: Map<String, String>,
) {
    fun toPartialResultsByPeriodExtractorRequest(
        etts: String,
        periodIdentified: String,
        courseIdentified: String,
        subjectIdentified: String
    ) =
        PartialResultsByPeriodExtractorRequest(
            sessionId = sessionId,
            etts = etts,
            periodIdentified = periodIdentified,
            courseIdentified = courseIdentified,
            subjectIdentified = subjectIdentified
        )
}