package com.github.sua.extraction.step.semesterresults

import com.github.sua.extraction.extractor.dto.DefaultExtractorParams
import com.github.sua.extraction.misc.httpclient.ConnectorHttpClient
import com.github.sua.extraction.step.Step
import com.github.sua.extraction.step.StepResponse

class DashboardSemesterResultsStep(
    private val httpClient: ConnectorHttpClient
) : Step() {

    fun doRequest(params: DefaultExtractorParams): StepResponse {
        val headers = mapOf(
            "Cookie" to "${params.sessionId}; ECS=S; ${params.etts}"
        )

        return httpClient.get(
            endpoint = params.endpoint,
            headers = headers
        ).getStepResponse()
    }

}