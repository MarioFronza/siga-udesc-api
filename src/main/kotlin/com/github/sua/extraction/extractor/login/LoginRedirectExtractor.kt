package com.github.sua.extraction.extractor.login

import com.github.sua.extraction.exception.ExtractorException
import com.github.sua.extraction.extractor.dto.DefaultExtractorParams
import com.github.sua.extraction.step.StepResponse.StepSuccess
import com.github.sua.extraction.step.login.LoginRedirectStep

class LoginRedirectExtractor(
    private val loginRedirectStep: LoginRedirectStep
) {

    fun extract(params: DefaultExtractorParams): DefaultExtractorParams {
        return when (val response = loginRedirectStep.doRequest(params)) {
            is StepSuccess -> DefaultExtractorParams(
                viewState = response.getViewState(),
                sessionId = response.getSessionId()
            )
            else -> throw ExtractorException("Login redirect extractor unexpected error")
        }
    }

}