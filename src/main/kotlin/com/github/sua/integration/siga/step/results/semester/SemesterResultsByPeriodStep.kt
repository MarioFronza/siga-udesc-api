package com.github.sua.integration.siga.step.results.semester

import com.github.sua.integration.siga.extraction.extractor.results.semester.SemesterResultsByPeriodExtractorRequest
import com.github.sua.integration.http.ConnectorHttpClient
import com.github.sua.integration.siga.step.Step
import com.github.sua.integration.siga.step.StepResponse

class SemesterResultsByPeriodStep(
    private val httpClient: ConnectorHttpClient
) : Step() {

    fun doRequest(request: SemesterResultsByPeriodExtractorRequest): StepResponse {
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
            "lCurso" to "",
            "estadoJanelaimportacao" to "fecha",
            "caracterSep" to "",
            "idConsulta" to "2",
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