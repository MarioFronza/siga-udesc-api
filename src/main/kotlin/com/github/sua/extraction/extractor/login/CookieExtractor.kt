package com.github.sua.extraction.extractor.login

import com.github.sua.extraction.exception.ExtractorException
import com.github.sua.extraction.parser.cookie.CookieParser
import com.github.sua.extraction.step.StepResponse
import com.github.sua.extraction.step.login.CookieStep

class CookieExtractor(
    private val cookieStep: CookieStep,
    private val cookieParser: CookieParser
) {

    fun extract(): CookieExtractorResponse {
        return when (val response = cookieStep.doRequest()) {
            is StepResponse.StepSuccess -> CookieExtractorResponse(
                endpoint = cookieParser.extractActionUrl(response.payload),
                viewState = response.getViewState(),
                sessionId = response.getSessionId()
            )
            else -> throw ExtractorException("Cookie extractor unexpected error")
        }
    }

}

data class CookieExtractorResponse(
    val sessionId: String,
    val endpoint: String,
    val viewState: String,
)