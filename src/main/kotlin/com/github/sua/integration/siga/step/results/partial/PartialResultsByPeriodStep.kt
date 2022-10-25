package com.github.sua.integration.siga.step.results.partial

import com.github.sua.integration.siga.extraction.extractor.results.partial.PartialResultsByPeriodExtractorRequest
import com.github.sua.integration.http.ConnectorHttpClient
import com.github.sua.integration.siga.step.Step
import com.github.sua.integration.siga.step.StepResponse

class PartialResultsByPeriodStep(
    private val httpClient: ConnectorHttpClient
) : Step() {

    fun doRequest(request: PartialResultsByPeriodExtractorRequest): StepResponse {
        val headers = mapOf(
            "Cookie" to "${request.sessionId}; ECS=S; ${request.etts}"
        )

        val body = mapOf(
            "lookupCorrentePlc" to "",
            "navSetaFocoPlc" to "",
            "modoPlc" to "consultaPlc",
            "detCorrPlc" to "",
            "indExcDetPlc" to "",
            "evento" to "Executar Consulta",
            "lPeriodoLetivo" to request.periodIdentified,
            "lCurso" to request.courseIdentified,
            "lDisciplina" to request.subjectIdentified,
            "caracterSep" to "",
            "idConsulta" to "3",
            "exibecomp" to "N",
            "exibeComboPerLet" to "S",
            "nomeArquivoPdf" to ""
        )

        return httpClient.post(
            endpoint = SEMESTER_RESULTS_BY_PERIOD_STEP_URL,
            headers = headers,
            body = body
        ).getStepResponse()
    }

    companion object {
        const val SEMESTER_RESULTS_BY_PERIOD_STEP_URL = "siga/com/executaconsultapersonaliz.do"
    }

}