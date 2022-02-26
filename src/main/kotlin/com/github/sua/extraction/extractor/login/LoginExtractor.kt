package com.github.sua.extraction.extractor.login

import com.github.sua.extraction.exception.ExtractorException
import com.github.sua.extraction.extractor.dto.DefaultExtractorParams
import com.github.sua.extraction.extractor.login.dto.LoginExtractorParams
import com.github.sua.extraction.step.StepResponse.StepSuccess
import com.github.sua.extraction.step.login.LoginStep

class LoginExtractor(
    private val loginStep: LoginStep
) {

    fun extract(params: LoginExtractorParams): DefaultExtractorParams {
        return when (val response = loginStep.doRequest(params)) {
            is StepSuccess -> DefaultExtractorParams(
                endpoint = response.getEndpoint(),
                sessionId = response.getSessionId()
            )
            else -> throw ExtractorException("Login extractor unexpected error")
        }
    }

}