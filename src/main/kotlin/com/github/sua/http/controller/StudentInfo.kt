package com.github.sua.http.controller

import com.github.sua.http.extension.getRequiredParameter
import com.github.sua.usecase.dto.input.credential.SigaCredentialInput
import com.github.sua.usecase.retrieve.RetrieveStudentInfo
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*

@Location("/student-info")
class StudentInfo

fun Route.studentInfo(
    service: RetrieveStudentInfo
) = get<StudentInfo> {
    val input = SigaCredentialInput(
        cpf = call.getRequiredParameter("cpf"),
        password = call.getRequiredParameter("password")
    )

    service.retrieve(input)
 
    call.respond(mapOf("retrieve" to "ok"))
}