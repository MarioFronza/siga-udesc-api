package com.github.sua.extraction.extractor.results.partial

import com.github.sua.extraction.exception.ExtractorException
import com.github.sua.extraction.extractor.results.partial.dto.PartialResults
import com.github.sua.extraction.parser.semesterresults.PartialResultsParser
import com.github.sua.extraction.parser.semesterresults.SemesterResultsParser
import com.github.sua.extraction.step.StepResponse.StepSuccess
import com.github.sua.extraction.step.results.partial.PartialResultsByPeriodStep
import com.github.sua.extraction.step.results.semester.SemesterResultsByPeriodStep

class PartialResultsByPeriodExtractor(
    private val partialResultsByPeriodStep: PartialResultsByPeriodStep,
    private val partialResultsParser: PartialResultsParser
) {

    fun extract(request: PartialResultsByPeriodExtractorRequest): PartialResultsByPeriodExtractorResponse {
        return when (val response = partialResultsByPeriodStep.doRequest(request)) {
            is StepSuccess -> PartialResultsByPeriodExtractorResponse(
                partialResults = partialResultsParser.extractPartialResults(
                    response.payload,
                    request.periodIdentified,
                    request.courseIdentified,
                    request.subjectIdentified,
                )
            )
            else -> throw ExtractorException("Partial results extractor unexpected error")
        }
    }

}

data class PartialResultsByPeriodExtractorRequest(
    val sessionId: String,
    val etts: String,
    val periodIdentified: String,
    val courseIdentified: String,
    val subjectIdentified: String
)

data class PartialResultsByPeriodExtractorResponse(
    val partialResults: PartialResults,
)