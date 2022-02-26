package com.github.sua.extraction.extractor.dashboard

import com.github.sua.extraction.exception.ExtractorException
import com.github.sua.extraction.extractor.dashboard.dto.DashboardExtractorResponse
import com.github.sua.extraction.extractor.dto.DefaultExtractorParams
import com.github.sua.extraction.parser.dashboard.DashboardParser
import com.github.sua.extraction.step.dashboard.DashboardStep
import com.github.sua.extraction.step.StepResponse.StepSuccess

class DashboardExtractor(
    private val dashboardStep: DashboardStep,
    private val dashboardParser: DashboardParser
) {

    fun extract(params: DefaultExtractorParams): DashboardExtractorResponse {
        return when (val response = dashboardStep.doRequest(params)) {
            is StepSuccess -> DashboardExtractorResponse(
                studentName = dashboardParser.extractStudentName(response.payload),
                defaultParams = params.copy(
                    sessionId = response.getSessionId(),
                    endpoint = dashboardParser.extractSemesterResultsUrl(response.payload),
                    etts = response.getEtts()
                )
            )
            else -> throw ExtractorException("Dashboard extractor unexpected error")
        }
    }

}