package com.github.sua.http.controller

import com.github.sua.http.extension.getRequiredParameter
import com.github.sua.usecase.retrieve.dto.input.SigaCredentialInput
import com.github.sua.usecase.retrieve.dto.input.PartialResultsIntegrationInput
import com.github.sua.usecase.retrieve.dto.input.PeriodInput
import com.github.sua.usecase.retrieve.RetrievePartialResults
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

@Location("/partial-results")
class PartialResults

fun Route.partialResults(
    service: RetrievePartialResults
) = get<PartialResults> {
    val input = PartialResultsIntegrationInput(
        sigaCredential = SigaCredentialInput(
            cpf = call.getRequiredParameter("cpf"),
            password = call.getRequiredParameter("password")
        ),
        period = PeriodInput(
            year = call.getRequiredParameter("year"),
            term = call.getRequiredParameter("term").toInt()
        ),
        course = call.getRequiredParameter("course"),
        subject = call.getRequiredParameter("subject")
    )

    val response = service.retrieve(input).getOutputContent()
    call.respond(response)
}