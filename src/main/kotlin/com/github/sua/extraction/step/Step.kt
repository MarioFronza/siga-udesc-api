package com.github.sua.extraction.step

import com.github.sua.extraction.step.StepResponse.StepError
import com.github.sua.extraction.step.StepResponse.StepSuccess
import com.github.sua.extraction.misc.httpclient.ConnectorHttpResponse


abstract class Step {

    fun ConnectorHttpResponse.getStepResponse() = with(this) {
        val body = getNotNullBody()
        if (isSuccessful) {
            StepSuccess(payload = body, headers = this.headers)
        } else {
            StepError(payload = body)
        }
    }

    private fun ConnectorHttpResponse.getNotNullBody() = body ?: throw Exception("Response with null body")
}