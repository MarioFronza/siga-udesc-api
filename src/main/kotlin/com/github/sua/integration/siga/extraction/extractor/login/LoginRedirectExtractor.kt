package com.github.sua.integration.siga.extraction.extractor.login

import com.github.sua.integration.exception.ExtractorException
import com.github.sua.integration.siga.step.StepResponse.StepSuccess
import com.github.sua.integration.siga.step.login.LoginRedirectStep

class LoginRedirectExtractor(
    private val loginRedirectStep: LoginRedirectStep
) {

    fun extract(request: LoginRedirectExtractorRequest): LoginRedirectExtractorResponse {
        return when (val response = loginRedirectStep.doRequest(request)) {
            is StepSuccess -> LoginRedirectExtractorResponse(
                viewState = response.getViewState(),
                etts= response.getEtts()
            )
            else -> throw ExtractorException("Login redirect extractor unexpected error")
        }
    }

}

data class LoginRedirectExtractorRequest(
    val sessionId: String,
    val endpoint: String
)

data class LoginRedirectExtractorResponse(
    val viewState: String,
    val etts: String
)