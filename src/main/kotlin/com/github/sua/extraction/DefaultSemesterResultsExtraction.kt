package com.github.sua.extraction

import com.github.sua.extraction.extractor.dashboard.DashboardExtractor
import com.github.sua.extraction.extractor.dashboard.DashboardExtractorRequest
import com.github.sua.extraction.extractor.dashboard.DashboardRedirectExtractor
import com.github.sua.extraction.extractor.dashboard.DashboardRedirectExtractorRequest
import com.github.sua.extraction.extractor.login.*
import com.github.sua.extraction.extractor.semesterresults.*
import com.github.sua.extraction.extractor.semesterresults.dto.StudentSemesterResults
import com.github.sua.usecase.dto.input.SemesterResultsInput
import com.github.sua.usecase.extraction.SemesterResultsExtraction

class DefaultSemesterResultsExtraction(
    private val cookieExtractor: CookieExtractor,
    private val loginExtractor: LoginExtractor,
    private val loginRedirectExtractor: LoginRedirectExtractor,
    private val dashboardRedirectExtractor: DashboardRedirectExtractor,
    private val dashboardExtractor: DashboardExtractor,
    private val dashboardSemesterResultsExtractor: DashboardSemesterResultsExtractor,
    private val semesterResultsExtractor: SemesterResultsExtractor,
    private val semesterResultsByPeriodExtractor: SemesterResultsByPeriodExtractor
) : SemesterResultsExtraction {

    override fun extract(input: SemesterResultsInput): StudentSemesterResults {
        val cookieExtractorResponse = cookieExtractor.extract()
        val loginExtractorResponse = loginExtractor.extract(
            LoginExtractorRequest(
                sessionId = cookieExtractorResponse.sessionId,
                endpoint = cookieExtractorResponse.endpoint,
                studentCpf = input.sigaCredential.cpf,
                studentPassword = input.sigaCredential.password,
                viewState = cookieExtractorResponse.viewState
            )
        )
        val loginRedirectExtractorResponse = loginRedirectExtractor.extract(
            LoginRedirectExtractorRequest(
                sessionId = cookieExtractorResponse.sessionId,
                endpoint = loginExtractorResponse.endpoint
            )
        )

        val dashboardRedirectResponse = dashboardRedirectExtractor.extract(
            DashboardRedirectExtractorRequest(
                sessionId = cookieExtractorResponse.sessionId,
                viewState = loginRedirectExtractorResponse.viewState
            )
        )

        val dashboardResponse = dashboardExtractor.extract(
            DashboardExtractorRequest(
                endpoint = dashboardRedirectResponse.endpoint,
                sessionId = cookieExtractorResponse.sessionId,
                etts = loginRedirectExtractorResponse.etts,
            )
        )

        val dashboardSemesterResultsExtractorResponse = dashboardSemesterResultsExtractor.extract(
            DashboardSemesterResultsExtractorRequest(
                endpoint = dashboardResponse.endpoint,
                sessionId = cookieExtractorResponse.sessionId,
                etts = loginRedirectExtractorResponse.etts
            )
        )

        val semesterResultsResponse = semesterResultsExtractor.extract(
            SemesterResultsExtractorRequest(
                endpoint = dashboardSemesterResultsExtractorResponse.endpoint,
                sessionId = cookieExtractorResponse.sessionId,
                etts = loginRedirectExtractorResponse.etts
            )
        )

        val periodIdentified = getPeriodSigaIdentified(
            year = input.period.year,
            term = input.period.term,
            periods = semesterResultsResponse.periodIdentified
        ) ?: throw Exception("Invalid period identified")

        val semesterResultsByPeriodExtractorResponse = semesterResultsByPeriodExtractor.extract(
            SemesterResultsByPeriodExtractorRequest(
                sessionId = semesterResultsResponse.sessionId,
                etts = loginRedirectExtractorResponse.etts,
                periodIdentified = periodIdentified
            )
        )

        return StudentSemesterResults(
            studentName = dashboardResponse.studentName,
            semesterResults = semesterResultsByPeriodExtractorResponse.semesterResults.toSemesterResultsPeriod()
        )
    }

    private fun getPeriodSigaIdentified(year: String, term: Int, periods: Map<String, String>) = periods["$year/$term"]

}