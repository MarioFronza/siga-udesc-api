package com.github.sua.http.controller

import com.github.sua.http.extension.getRequiredParameter
import com.github.sua.usecase.retrieve.dto.input.SigaCredentialInput
import com.github.sua.usecase.retrieve.dto.input.PeriodInput
import com.github.sua.usecase.retrieve.dto.input.SemesterResultsIntegrationInput
import com.github.sua.usecase.retrieve.RetrieveSemesterResults
import com.github.sua.usecase.retrieve.dto.output.RetrieveOutput.RetrieveError
import com.github.sua.usecase.retrieve.dto.output.RetrieveOutput.RetrieveSuccess
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

@Location("/semester-results")
class SemesterResults

fun Route.semesterResults(
    service: RetrieveSemesterResults
) = get<SemesterResults> {
    val input = SemesterResultsIntegrationInput(
        sigaCredential = SigaCredentialInput(
            cpf = call.getRequiredParameter("cpf"),
            password = call.getRequiredParameter("password")
        ),
        period = PeriodInput(
            year = call.getRequiredParameter("year"),
            term = call.getRequiredParameter("term").toInt()
        )
    )
    val response = service.retrieve(input).getOutputContent()
    call.respond(response)
}