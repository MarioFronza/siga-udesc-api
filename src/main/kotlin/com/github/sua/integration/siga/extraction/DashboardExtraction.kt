package com.github.sua.integration.siga.extraction

import com.github.sua.integration.siga.extraction.extractor.dashboard.DashboardExtractor
import com.github.sua.integration.siga.extraction.extractor.dashboard.DashboardExtractorRequest
import com.github.sua.integration.siga.extraction.extractor.dashboard.DashboardRedirectExtractor
import com.github.sua.integration.siga.extraction.extractor.dashboard.DashboardRedirectExtractorRequest
import com.github.sua.integration.siga.extraction.extractor.login.*
import com.github.sua.integration.siga.extraction.extractor.results.partial.DashboardPartialResultsExtractorRequest
import com.github.sua.integration.siga.extraction.extractor.results.semester.DashboardSemesterResultsExtractorRequest
import com.github.sua.integration.siga.extraction.dto.DashboardInput

class DashboardExtraction(
    private val cookieExtractor: CookieExtractor,
    private val loginExtractor: LoginExtractor,
    private val loginRedirectExtractor: LoginRedirectExtractor,
    private val dashboardRedirectExtractor: DashboardRedirectExtractor,
    private val dashboardExtractor: DashboardExtractor,
) {

    fun extract(input: DashboardInput, resultPageType: String): DashboardExtractionResponse {
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
            ),
            resultPageType = resultPageType
        )

        return DashboardExtractionResponse(
            studentName = dashboardResponse.studentName,
            dashboardResponseEndpoint = dashboardResponse.endpoint,
            cookieExtractorResponseSessionId = cookieExtractorResponse.sessionId,
            loginRedirectExtractorResponseEtts = loginRedirectExtractorResponse.etts,
        )
    }

}

data class DashboardExtractionResponse(
    val studentName: String,
    val dashboardResponseEndpoint: String,
    val cookieExtractorResponseSessionId: String,
    val loginRedirectExtractorResponseEtts: String
) {
    fun toDashboardSemesterResultsExtractorRequest() = DashboardSemesterResultsExtractorRequest(
        endpoint = dashboardResponseEndpoint,
        sessionId = cookieExtractorResponseSessionId,
        etts = loginRedirectExtractorResponseEtts
    )

    fun toDashboardPartialResultsExtractorRequest() = DashboardPartialResultsExtractorRequest(
        endpoint = dashboardResponseEndpoint,
        sessionId = cookieExtractorResponseSessionId,
        etts = loginRedirectExtractorResponseEtts
    )
}