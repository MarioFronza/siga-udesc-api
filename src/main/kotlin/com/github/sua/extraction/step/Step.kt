package com.github.sua.extraction.step

import com.github.sua.extraction.dto.StepResponse.StepError
import com.github.sua.extraction.dto.StepResponse.StepSuccess
import com.github.sua.extraction.misc.httpclient.StepHttpResponse


abstract class Step {

    fun StepHttpResponse.getStepResponse() = with(this) {
        val body = getNotNullBody()
        if (isSuccessful) {
            StepSuccess(payload = body)
        } else {
            StepError(payload = body)
        }
    }

    private fun StepHttpResponse.getNotNullBody() = body ?: throw Exception("Response with null body")
}