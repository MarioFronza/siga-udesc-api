package com.github.sua.extraction.extractor.cookie

import com.github.sua.extraction.dto.StepResponse
import com.github.sua.extraction.extractor.cookie.dto.output.CookieExtractorOutput
import com.github.sua.extraction.parser.cookie.CookieParser
import com.github.sua.extraction.step.login.CookieStep

class CookieExtractor(
    private val cookieStep: CookieStep,
    private val cookieParser: CookieParser
) {

    fun extract(): CookieExtractorOutput {
        val actionUrl = when (val response = cookieStep.doRequest()) {
            is StepResponse.StepSuccess -> {
                cookieParser.extractAction(response.payload)
            }
            else -> throw Exception("Cookie parser unexpected error")
        }
        return CookieExtractorOutput(url = actionUrl)
    }

}