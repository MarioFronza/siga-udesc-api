package com.github.sua.http.plugins

import com.github.sua.http.controller.healthCheck
import com.github.sua.http.controller.partialResults
import com.github.sua.http.controller.semesterResults
import com.github.sua.http.controller.studentInfo
import com.github.sua.usecase.retrieve.RetrievePartialResults
import com.github.sua.usecase.retrieve.RetrieveSemesterResults
import com.github.sua.usecase.retrieve.RetrieveStudentInfo
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    install(Locations)

    val retrieveStudentInfo by inject<RetrieveStudentInfo>()
    val retrieveSemesterResults by inject<RetrieveSemesterResults>()
    val retrievePartialResults by inject<RetrievePartialResults>()

    routing {
        healthCheck()
        studentInfo(retrieveStudentInfo)
        semesterResults(retrieveSemesterResults)
        partialResults(retrievePartialResults)
    }
}