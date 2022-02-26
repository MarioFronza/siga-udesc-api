package com.github.sua.extraction

import com.github.sua.entity.student.SigaCredential
import com.github.sua.extraction.extractor.dashboard.DashboardExtractor
import com.github.sua.extraction.extractor.dashboard.dto.DashboardExtractorParams
import com.github.sua.extraction.extractor.dto.DefaultExtractorParams
import com.github.sua.extraction.extractor.login.CookieExtractor
import com.github.sua.extraction.extractor.login.LoginExtractor
import com.github.sua.extraction.extractor.login.LoginRedirectExtractor
import com.github.sua.extraction.extractor.login.dto.LoginExtractorParams
import com.github.sua.usecase.dto.input.SemesterResultsInput
import com.github.sua.usecase.extraction.SemesterResultsExtraction

class DefaultSemesterResultsExtraction(
    private val cookieExtractor: CookieExtractor,
    private val loginExtractor: LoginExtractor,
    private val loginRedirectExtractor: LoginRedirectExtractor,
    private val dashboardRedirectExtractor: LoginRedirectExtractor,
    private val dashboardExtractor: DashboardExtractor
) : SemesterResultsExtraction {


    override fun extract(input: SemesterResultsInput) {
        val extractionParams = extractLogin(credential = input.sigaCredential)
        val dashboardResponse = extractDashboard(extractionParams)
        print(dashboardResponse)
    }

    private fun extractLogin(credential: SigaCredential): DefaultExtractorParams {
        val cookieResponse = cookieExtractor.extract()
        val loginResponse = loginExtractor.extract(loginParamsFrom(cookieResponse, credential))
        return loginRedirectExtractor.extract(loginResponse)
    }

    private fun extractDashboard(params: DefaultExtractorParams): DashboardExtractorParams {
        val dashboardRedirectResponse = dashboardRedirectExtractor.extract(params)
        return dashboardExtractor.extract(dashboardRedirectResponse)
    }

    private fun loginParamsFrom(params: DefaultExtractorParams, credential: SigaCredential) = LoginExtractorParams(
        studentCpf = credential.cpf,
        studentPassword = credential.password,
        defaultParams = params
    )

}