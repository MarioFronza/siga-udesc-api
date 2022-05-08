package com.github.sua.http.controller

import com.github.sua.http.extension.getRequiredParameter
import com.github.sua.usecase.dto.input.credential.SigaCredentialInput
import com.github.sua.usecase.dto.input.period.PeriodInput
import com.github.sua.usecase.dto.input.extraction.SemesterResultsInput
import com.github.sua.usecase.retrieve.RetrieveSemesterResults
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*

@Location("/semester-results")
class SemesterResults

fun Route.semesterResults(
    service: RetrieveSemesterResults
) = get<SemesterResults> {
    val input = SemesterResultsInput(
        sigaCredential = SigaCredentialInput(
            cpf = call.getRequiredParameter("cpf"),
            password = call.getRequiredParameter("password")
        ),
        period = PeriodInput(
            year = call.getRequiredParameter("year"),
            term = call.getRequiredParameter("term").toInt()
        )
    )

    val response = service.retrieve(input)

    call.respond(response)
}