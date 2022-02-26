package com.github.sua.extraction.step.semesterresults

import com.github.sua.extraction.misc.httpclient.ConnectorHttpClient
import com.github.sua.extraction.step.Step
import com.github.sua.extraction.step.dto.SemesterResultsByPeriodStepInput
import com.github.sua.extraction.step.StepResponse

class SemesterResultsByPeriodStep(
    private val httpClient: ConnectorHttpClient
) : Step() {

    fun doRequest(input: SemesterResultsByPeriodStepInput): StepResponse {
        val headers = mapOf(
            "Cookie" to "${input.headerSessionId}; ECS=S; ${input.etts}"
        )

        val body = mapOf(
            "lookupCorrentePlc" to "",
            "navSetaFocoPlc" to "",
            "modoPlc" to "consultaPlc",
            "detCorrPlc" to "",
            "indExcDetPlc" to "",
            "evento" to "Executar Consulta",
            "lPeriodoLetivo" to input.periodIdentified,
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