package com.github.sua.extraction

import com.github.sua.extraction.extractor.dashboard.DashboardExtractor
import com.github.sua.extraction.extractor.dto.DefaultExtractorParams
import com.github.sua.extraction.extractor.login.CookieExtractor
import com.github.sua.extraction.extractor.login.LoginExtractor
import com.github.sua.extraction.extractor.login.LoginRedirectExtractor
import com.github.sua.extraction.extractor.login.dto.LoginExtractorParams
import com.github.sua.extraction.step.dashboard.DashboardRedirectStep
import com.github.sua.extraction.step.dashboard.DashboardStep
import com.github.sua.usecase.dto.input.SemesterResultsInput
import com.github.sua.usecase.extraction.SemesterResultsExtraction

class DefaultSemesterResultsExtraction(
    private val cookieExtractor: CookieExtractor,
    private val loginExtractor: LoginExtractor,
    private val loginRedirectExtractor: LoginRedirectExtractor,
    private val dashboardRedirectExtractor: LoginRedirectExtractor,
    private val dashboardExtractor: DashboardExtractor
) : SemesterResultsExtraction {

    private var defaultExtractorParams = DefaultExtractorParams()

    override fun extract(input: SemesterResultsInput) {
        cookieExtractor.extract().also { updateExtractorParams(it) }
        loginExtractor.extract(
            LoginExtractorParams(
                studentCpf = input.sigaCredential.cpf,
                studentPassword = input.sigaCredential.password,
                defaultParams = defaultExtractorParams
            )
        ).also { updateExtractorParams(it) }
        loginRedirectExtractor.extract(defaultExtractorParams).also { updateExtractorParams(it) }
        dashboardRedirectExtractor.extract(defaultExtractorParams).also { updateExtractorParams(it) }

        val dashboardResponse = dashboardExtractor.extract(defaultExtractorParams)
        print(dashboardResponse)
    }

    private fun updateExtractorParams(newParams: DefaultExtractorParams) {
        defaultExtractorParams = newParams
    }

}