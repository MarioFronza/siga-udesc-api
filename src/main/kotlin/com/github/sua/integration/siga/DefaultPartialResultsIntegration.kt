package com.github.sua.integration.siga

import com.github.sua.integration.siga.extraction.DashboardExtraction
import com.github.sua.integration.siga.extraction.extractor.results.partial.*
import com.github.sua.integration.siga.extraction.misc.utils.getIdentifiedBy
import com.github.sua.integration.siga.parser.dashboard.DashboardParser
import com.github.sua.integration.siga.extraction.dto.DashboardInput.Companion.fromPartialResultsIntegrationInput
import com.github.sua.usecase.integration.PartialResultsIntegration
import com.github.sua.usecase.integration.dto.IntegrationOutput
import com.github.sua.usecase.integration.dto.IntegrationOutput.IntegrationSuccess
import com.github.sua.usecase.integration.dto.IntegrationOutput.IntegrationError
import com.github.sua.usecase.retrieve.dto.input.PartialResultsIntegrationInput
import com.github.sua.usecase.retrieve.dto.output.StudentPartialResultsIntegrationOutput

class DefaultPartialResultsIntegration(
    private val dashboardExtraction: DashboardExtraction,
    private val dashboardPartialResultsExtractor: DashboardPartialResultsExtractor,
    private val partialResultsExtractor: PartialResultsExtractor,
    private val partialResultsByPeriodExtractor: PartialResultsByPeriodExtractor
) : PartialResultsIntegration {

    override fun integrate(input: PartialResultsIntegrationInput): IntegrationOutput<StudentPartialResultsIntegrationOutput> {
        try {
            val dashboardExtractionResponse = dashboardExtraction.extract(
                input = fromPartialResultsIntegrationInput(input),
                resultPageType = DashboardParser.PARTIAL_RESULT_PAGE
            )

            val dashboardPartialResultsExtractorResponse = dashboardPartialResultsExtractor.extract(
                request = dashboardExtractionResponse.toDashboardPartialResultsExtractorRequest()
            )

            val partialResultsResponse = partialResultsExtractor.extract(
                request = dashboardPartialResultsExtractorResponse.toPartialResultsExtractorRequest(
                    sessionId = dashboardExtractionResponse.cookieExtractorResponseSessionId,
                    etts = dashboardExtractionResponse.loginRedirectExtractorResponseEtts
                )
            )

            val partialResultsByPeriodExtractorResponse = partialResultsByPeriodExtractor.extract(
                request = partialResultsResponse.toPartialResultsByPeriodExtractorRequest(
                    etts = dashboardExtractionResponse.loginRedirectExtractorResponseEtts,
                    periodIdentified = partialResultsResponse.periodsIdentified.getIdentifiedBy(
                        key = input.period.toKeyString()
                    ),
                    courseIdentified = partialResultsResponse.coursesIdentified.getIdentifiedBy(key = input.course),
                    subjectIdentified = partialResultsResponse.subjectsIdentified.getIdentifiedBy(key = input.subject)
                )
            )

            return IntegrationSuccess(
                data = StudentPartialResultsIntegrationOutput(
                    studentName = dashboardExtractionResponse.studentName,
                    partialResults = partialResultsByPeriodExtractorResponse.partialResults
                )
            )
        } catch (e: Exception) {
            return IntegrationError(
                message = "Partial Results integration unexpected error: ${e.message}"
            )
        }
    }

}