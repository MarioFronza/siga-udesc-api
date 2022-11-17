package com.github.sua.http.controller

import com.github.sua.http.extension.getRequiredParameter
import com.github.sua.usecase.retrieve.dto.input.SigaCredentialInput
import com.github.sua.usecase.retrieve.RetrieveStudentInfo
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

@Location("/student-info")
class StudentInfo

fun Route.studentInfo(
    service: RetrieveStudentInfo
) = get<StudentInfo> {
    val input = SigaCredentialInput(
        cpf = call.getRequiredParameter("cpf"),
        password = call.getRequiredParameter("password")
    )
    val response = service.retrieve(input).getOutputContent()
    call.respond(response)
}