package com.github.sua.integration.siga.extraction.extractor.login

import com.github.sua.integration.exception.ExtractorException
import com.github.sua.integration.siga.step.StepResponse.StepSuccess
import com.github.sua.integration.siga.step.login.LoginStep

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