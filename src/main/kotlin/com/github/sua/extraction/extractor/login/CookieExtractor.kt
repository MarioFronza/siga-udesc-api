package com.github.sua.extraction.extractor.login

import com.github.sua.extraction.exception.ExtractorException
import com.github.sua.extraction.extractor.dto.DefaultExtractorParams
import com.github.sua.extraction.parser.cookie.CookieParser
import com.github.sua.extraction.step.StepResponse
import com.github.sua.extraction.step.login.CookieStep

class CookieExtractor(
    private val cookieStep: CookieStep,
    private val cookieParser: CookieParser
) {

    fun extract(): DefaultExtractorParams {
        return when (val response = cookieStep.doRequest()) {
            is StepResponse.StepSuccess -> DefaultExtractorParams(
                endpoint = cookieParser.extractActionUrl(response.payload)
            )
            else -> throw ExtractorException("Cookie extractor unexpected error")
        }
    }

}