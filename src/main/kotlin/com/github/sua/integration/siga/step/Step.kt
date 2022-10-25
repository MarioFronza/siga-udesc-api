package com.github.sua.integration.siga.step

import com.github.sua.integration.siga.step.StepResponse.StepError
import com.github.sua.integration.siga.step.StepResponse.StepSuccess
import com.github.sua.integration.http.ConnectorHttpResponse


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