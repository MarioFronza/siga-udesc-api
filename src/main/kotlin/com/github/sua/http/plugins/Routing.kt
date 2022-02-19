package com.github.sua.http.plugins

import com.github.sua.http.controller.healthCheck
import com.github.sua.http.controller.studentInfo
import com.github.sua.usecase.RetrieveStudentInfo
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.locations.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    install(DefaultHeaders)
    install(Locations)
    install(CallLogging)

    val retrieveStudentInfo by inject<RetrieveStudentInfo>()

    routing {
        healthCheck()
        studentInfo(retrieveStudentInfo)
    }
}