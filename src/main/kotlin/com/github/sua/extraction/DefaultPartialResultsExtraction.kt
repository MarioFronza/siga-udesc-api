package com.github.sua.extraction

import com.github.sua.extraction.extractor.results.partial.*
import com.github.sua.usecase.dto.output.extraction.StudentPartialOutput
import com.github.sua.extraction.misc.utils.getIdentifiedBy
import com.github.sua.extraction.parser.dashboard.DashboardParser.Companion.PARTIAL_RESULT_PAGE
import com.github.sua.usecase.dto.input.extraction.PartialResultsInput
import com.github.sua.usecase.extraction.PartialResultsExtraction

class DefaultPartialResultsExtraction(
    private val dashboardExtraction: DashboardExtraction,
    private val dashboardPartialResultsExtractor: DashboardPartialResultsExtractor,
    private val partialResultsExtractor: PartialResultsExtractor,
    private val partialResultsByPeriodExtractor: PartialResultsByPeriodExtractor
) : PartialResultsExtraction {

    override fun extract(input: PartialResultsInput): StudentPartialOutput {
        val dashboardExtractionResponse = dashboardExtraction.extract(
            input = input.toDashboardInput(),
            resultPageType = PARTIAL_RESULT_PAGE
        )

        val dashboardPartialResultsExtractorResponse = dashboardPartialResultsExtractor.extract(
            DashboardPartialResultsExtractorRequest(
                endpoint = dashboardExtractionResponse.dashboardResponseEndpoint,
                sessionId = dashboardExtractionResponse.cookieExtractorResponseSessionId,
                etts = dashboardExtractionResponse.loginRedirectExtractorResponseEtts
            )
        )

        val partialResultsResponse = partialResultsExtractor.extract(
            PartialResultsExtractorRequest(
                endpoint = dashboardPartialResultsExtractorResponse.endpoint,
                sessionId = dashboardExtractionResponse.cookieExtractorResponseSessionId,
                etts = dashboardExtractionResponse.loginRedirectExtractorResponseEtts
            )
        )

        val periodIdentified = partialResultsResponse.periodsIdentified.getIdentifiedBy(
            key = "${input.period.year}/${input.period.term}"
        )
        val courseIdentified = partialResultsResponse.coursesIdentified.getIdentifiedBy(key= input.course)
        val subjectIdentified = partialResultsResponse.subjectsIdentified.getIdentifiedBy(key = input.subject)

        val partialResultsByPeriodExtractorResponse = partialResultsByPeriodExtractor.extract(
            PartialResultsByPeriodExtractorRequest(
                sessionId = partialResultsResponse.sessionId,
                etts = dashboardExtractionResponse.loginRedirectExtractorResponseEtts,
                periodIdentified = periodIdentified,
                courseIdentified = courseIdentified,
                subjectIdentified = subjectIdentified
            )
        )

        return StudentPartialOutput(
            studentName = dashboardExtractionResponse.studentName,
            partialResults = partialResultsByPeriodExtractorResponse.partialResults
        )
    }

}