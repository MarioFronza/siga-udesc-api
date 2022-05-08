package com.github.sua.http.controller

import com.github.sua.http.extension.getRequiredParameter
import com.github.sua.usecase.dto.input.credential.SigaCredentialInput
import com.github.sua.usecase.dto.input.extraction.PartialResultsInput
import com.github.sua.usecase.dto.input.period.PeriodInput
import com.github.sua.usecase.retrieve.RetrievePartialResults
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*

@Location("/partial-results")
class PartialResults

fun Route.partialResults(
    service: RetrievePartialResults
) = get<PartialResults> {
    val input = PartialResultsInput(
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

    val response = service.retrieve(input)

    call.respond(response)
}