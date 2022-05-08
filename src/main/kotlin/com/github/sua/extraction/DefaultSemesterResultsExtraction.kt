package com.github.sua.extraction

import com.github.sua.extraction.extractor.results.semester.*
import com.github.sua.usecase.dto.output.extraction.StudentSemesterOutput
import com.github.sua.extraction.misc.utils.getIdentifiedBy
import com.github.sua.extraction.parser.dashboard.DashboardParser.Companion.SEMESTER_RESULT_PAGE
import com.github.sua.usecase.dto.input.extraction.SemesterResultsInput
import com.github.sua.usecase.extraction.SemesterResultsExtraction

class DefaultSemesterResultsExtraction(
    private val dashboardExtraction: DashboardExtraction,
    private val dashboardSemesterResultsExtractor: DashboardSemesterResultsExtractor,
    private val semesterResultsExtractor: SemesterResultsExtractor,
    private val semesterResultsByPeriodExtractor: SemesterResultsByPeriodExtractor
) : SemesterResultsExtraction {

    override fun extract(input: SemesterResultsInput): StudentSemesterOutput {
        val dashboardExtractionResponse = dashboardExtraction.extract(
            input = input.toDashboardInput(),
            resultPageType = SEMESTER_RESULT_PAGE
        )

        val dashboardSemesterResultsExtractorResponse = dashboardSemesterResultsExtractor.extract(
            DashboardSemesterResultsExtractorRequest(
                endpoint = dashboardExtractionResponse.dashboardResponseEndpoint,
                sessionId = dashboardExtractionResponse.cookieExtractorResponseSessionId,
                etts = dashboardExtractionResponse.loginRedirectExtractorResponseEtts
            )
        )

        val semesterResultsResponse = semesterResultsExtractor.extract(
            SemesterResultsExtractorRequest(
                endpoint = dashboardSemesterResultsExtractorResponse.endpoint,
                sessionId = dashboardExtractionResponse.cookieExtractorResponseSessionId,
                etts = dashboardExtractionResponse.loginRedirectExtractorResponseEtts
            )
        )

        val periodIdentified = semesterResultsResponse.periodsIdentified.getIdentifiedBy(
            key = "${input.period.year}/${input.period.term}"
        )

        val semesterResultsByPeriodExtractorResponse = semesterResultsByPeriodExtractor.extract(
            SemesterResultsByPeriodExtractorRequest(
                sessionId = semesterResultsResponse.sessionId,
                etts = dashboardExtractionResponse.loginRedirectExtractorResponseEtts,
                periodIdentified = periodIdentified
            )
        )

        return StudentSemesterOutput(
            studentName = dashboardExtractionResponse.studentName,
            semesterResults = semesterResultsByPeriodExtractorResponse.semesterResults.toSemesterResultsPeriod()
        )
    }

}