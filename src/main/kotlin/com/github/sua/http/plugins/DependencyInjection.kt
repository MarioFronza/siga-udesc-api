package com.github.sua.http.plugins

import com.github.sua.usecase.RetrieveStudentInfo
import io.ktor.application.*
import org.koin.dsl.module
import org.koin.dsl.single
import org.koin.ktor.ext.Koin

fun Application.configureDI() {
    install(Koin) {
        modules(appModule)
    }
}


val appModule = module(createdAtStart = true) {
    single<RetrieveStudentInfo>()
}