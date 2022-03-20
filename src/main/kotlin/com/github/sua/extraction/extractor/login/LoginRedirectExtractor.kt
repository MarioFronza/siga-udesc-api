package com.github.sua.extraction.extractor.login

import com.github.sua.extraction.exception.ExtractorException
import com.github.sua.extraction.step.StepResponse.StepSuccess
import com.github.sua.extraction.step.login.LoginRedirectStep

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