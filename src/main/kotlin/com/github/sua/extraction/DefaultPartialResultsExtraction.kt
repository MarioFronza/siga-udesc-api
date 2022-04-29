package com.github.sua.extraction

import com.github.sua.extraction.extractor.dashboard.DashboardExtractor
import com.github.sua.extraction.extractor.dashboard.DashboardExtractorRequest
import com.github.sua.extraction.extractor.dashboard.DashboardRedirectExtractor
import com.github.sua.extraction.extractor.dashboard.DashboardRedirectExtractorRequest
import com.github.sua.extraction.extractor.login.*
import com.github.sua.extraction.extractor.results.semester.*
import com.github.sua.extraction.extractor.results.*
import com.github.sua.extraction.extractor.results.partial.*
import com.github.sua.extraction.extractor.results.partial.dto.StudentPartialResults
import com.github.sua.usecase.dto.input.PartialResultsInput
import com.github.sua.usecase.extraction.PartialResultsExtraction

class DefaultPartialResultsExtraction(
    private val cookieExtractor: CookieExtractor,
    private val loginExtractor: LoginExtractor,
    private val loginRedirectExtractor: LoginRedirectExtractor,
    private val dashboardRedirectExtractor: DashboardRedirectExtractor,
    private val dashboardExtractor: DashboardExtractor,
    private val dashboardPartialResultsExtractor: DashboardPartialResultsExtractor,
    private val partialResultsExtractor: PartialResultsExtractor,
    private val partialResultsByPeriodExtractor: PartialResultsByPeriodExtractor
) : PartialResultsExtraction {

    override fun extract(input: PartialResultsInput): StudentPartialResults {
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

        val dashboardPartialResultsExtractorResponse = dashboardPartialResultsExtractor.extract(
            DashboardPartialResultsExtractorRequest(
                endpoint = dashboardResponse.endpoint,
                sessionId = cookieExtractorResponse.sessionId,
                etts = loginRedirectExtractorResponse.etts
            )
        )

        val partialResultsResponse = partialResultsExtractor.extract(
            PartialResultsExtractorRequest(
                endpoint = dashboardPartialResultsExtractorResponse.endpoint,
                sessionId = cookieExtractorResponse.sessionId,
                etts = loginRedirectExtractorResponse.etts
            )
        )

        val periodIdentified = getPeriodSigaIdentified(
            year = input.period.year,
            term = input.period.term,
            periods = partialResultsResponse.periodsIdentified
        ) ?: throw Exception("Invalid period identified")

        val courseIdentified = getSigaIdentifiedBy(
            content = input.course,
            items = partialResultsResponse.coursesIdentified
        ) ?: throw Exception("Invalid course identified")

        val subjectIdentified = getSigaIdentifiedBy(
            content = input.course,
            items = partialResultsResponse.subjectsIdentified
        ) ?: throw Exception("Invalid subject identified")

        val partialResultsByPeriodExtractorResponse = partialResultsByPeriodExtractor.extract(
            PartialResultsByPeriodExtractorRequest(
                sessionId = partialResultsResponse.sessionId,
                etts = loginRedirectExtractorResponse.etts,
                periodIdentified = periodIdentified,
                courseIdentified = courseIdentified,
                subjectIdentified = subjectIdentified
            )
        )

        return StudentPartialResults(
            studentName = dashboardResponse.studentName,
            partialResults = partialResultsByPeriodExtractorResponse.partialResults
        )
    }

    private fun getPeriodSigaIdentified(year: String, term: Int, periods: Map<String, String>) = periods["$year/$term"]

    private fun getSigaIdentifiedBy(content: String, items: Map<String, String>) = items[content]

}