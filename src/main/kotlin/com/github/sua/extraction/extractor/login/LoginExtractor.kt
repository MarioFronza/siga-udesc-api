package com.github.sua.extraction.extractor.login

import com.github.sua.extraction.exception.ExtractorException
import com.github.sua.extraction.step.StepResponse.StepSuccess
import com.github.sua.extraction.step.login.LoginStep

class LoginExtractor(
    private val loginStep: LoginStep
) {

    fun extract(request: LoginExtractorRequest): LoginExtractorResponse {
        return when (val response = loginStep.doRequest(request)) {
            is StepSuccess -> LoginExtractorResponse(
                endpoint = response.getEndpoint()
            )
            else -> throw ExtractorException("Login extractor unexpected error")
        }
    }

}

data class LoginExtractorRequest(
    val studentCpf: String,
    val studentPassword: String,
    val sessionId: String,
    val endpoint: String,
    val viewState: String
)

data class LoginExtractorResponse(
    val endpoint: String
)