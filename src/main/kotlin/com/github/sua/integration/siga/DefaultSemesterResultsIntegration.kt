package com.github.sua.integration.siga

import com.github.sua.integration.siga.extraction.DashboardExtraction
import com.github.sua.integration.siga.extraction.extractor.results.semester.DashboardSemesterResultsExtractor
import com.github.sua.integration.siga.extraction.extractor.results.semester.SemesterResultsByPeriodExtractor
import com.github.sua.integration.siga.extraction.extractor.results.semester.SemesterResultsExtractor
import com.github.sua.integration.siga.extraction.misc.utils.getIdentifiedBy
import com.github.sua.integration.siga.parser.dashboard.DashboardParser.Companion.SEMESTER_RESULT_PAGE
import com.github.sua.usecase.dto.input.extraction.DashboardInput.Companion.fromSemesterResultsIntegrationInput
import com.github.sua.usecase.integration.SemesterResultsIntegration
import com.github.sua.usecase.integration.dto.IntegrationOutput
import com.github.sua.usecase.integration.dto.IntegrationOutput.IntegrationSuccess
import com.github.sua.usecase.integration.dto.period.PeriodInput
import com.github.sua.usecase.retrieve.dto.SemesterResultsIntegrationInput
import com.github.sua.usecase.retrieve.dto.StudentSemesterResultsIntegrationOutput

class DefaultSemesterResultsIntegration(
    private val dashboardExtraction: DashboardExtraction,
    private val dashboardSemesterResultsExtractor: DashboardSemesterResultsExtractor,
    private val semesterResultsExtractor: SemesterResultsExtractor,
    private val semesterResultsByPeriodExtractor: SemesterResultsByPeriodExtractor
) : SemesterResultsIntegration {

    override fun integrate(input: SemesterResultsIntegrationInput): IntegrationOutput<StudentSemesterResultsIntegrationOutput> {
        val dashboardExtractionResponse = dashboardExtraction.extract(
            input = fromSemesterResultsIntegrationInput(input),
            resultPageType = SEMESTER_RESULT_PAGE
        )

        val dashboardSemesterResultsExtractorResponse = dashboardSemesterResultsExtractor.extract(
            request = dashboardExtractionResponse.toDashboardSemesterResultsExtractorRequest()
        )

        val semesterResultsResponse = semesterResultsExtractor.extract(
            request = dashboardSemesterResultsExtractorResponse.toSemesterResultsExtractorRequest(
                sessionId = dashboardExtractionResponse.cookieExtractorResponseSessionId,
                etts = dashboardExtractionResponse.loginRedirectExtractorResponseEtts
            )
        )

        val periodIdentified = semesterResultsResponse.periodsIdentified.getIdentifiedBy(
            key = getPeriodKeyBy(input.period)
        )

        val semesterResultsByPeriodExtractorResponse = semesterResultsByPeriodExtractor.extract(
            request = semesterResultsResponse.toSemesterResultsByPeriodExtractorRequest(
                etts = dashboardExtractionResponse.loginRedirectExtractorResponseEtts,
                periodIdentified = periodIdentified
            )
        )

        return IntegrationSuccess(
            data = StudentSemesterResultsIntegrationOutput(
                studentName = dashboardExtractionResponse.studentName,
                semesterResults = semesterResultsByPeriodExtractorResponse.semesterResults.toSemesterResultsPeriod()
            )
        )
    }

    private fun getPeriodKeyBy(period: PeriodInput) = "${period.year}/${period.term}"

}