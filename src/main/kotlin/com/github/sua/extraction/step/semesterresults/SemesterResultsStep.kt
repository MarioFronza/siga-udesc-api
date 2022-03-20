package com.github.sua.extraction.step.semesterresults

import com.github.sua.extraction.extractor.semesterresults.SemesterResultsExtractorRequest
import com.github.sua.extraction.misc.httpclient.ConnectorHttpClient
import com.github.sua.extraction.step.Step
import com.github.sua.extraction.step.StepResponse

class SemesterResultsStep(
    private val httpClient: ConnectorHttpClient
) : Step() {

    fun doRequest(request: SemesterResultsExtractorRequest): StepResponse {
        val headers = mapOf(
            "Cookie" to "${request.sessionId}; ECS=S; ${request.etts}"
        )

        return httpClient.get(
            endpoint = request.endpoint,
            headers = headers
        ).getStepResponse()
    }

}