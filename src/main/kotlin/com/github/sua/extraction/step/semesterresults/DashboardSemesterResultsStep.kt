package com.github.sua.extraction.step.semesterresults

import com.github.sua.extraction.misc.httpclient.ConnectorHttpClient
import com.github.sua.extraction.step.Step
import com.github.sua.extraction.step.dto.DashboardSemesterResultsStepInput
import com.github.sua.extraction.step.StepResponse

class DashboardSemesterResultsStep(
    private val httpClient: ConnectorHttpClient
) : Step() {

    fun doRequest(input: DashboardSemesterResultsStepInput): StepResponse {
        val headers = mapOf(
            "Cookie" to "${input.headerSessionId}; ECS=S; ${input.etts}"
        )

        return httpClient.get(
            endpoint = input.endpoint,
            headers = headers
        ).getStepResponse()
    }

}