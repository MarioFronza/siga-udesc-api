package com.github.sua.http.plugins

import com.github.sua.extraction.DefaultSemesterResultsExtraction
import com.github.sua.extraction.extractor.login.CookieExtractor
import com.github.sua.extraction.misc.httpclient.ConnectorHttpClient
import com.github.sua.extraction.misc.httpclient.ktor.KtorHttpClient
import com.github.sua.extraction.parser.cookie.CookieParser
import com.github.sua.extraction.step.dashboard.DashboardRedirectStep
import com.github.sua.extraction.step.dashboard.DashboardStep
import com.github.sua.extraction.step.login.CookieStep
import com.github.sua.extraction.step.login.LoginRedirectStep
import com.github.sua.extraction.step.login.LoginStep
import com.github.sua.usecase.extraction.SemesterResultsExtraction
import com.github.sua.usecase.retrieve.RetrieveSemesterResults
import com.github.sua.usecase.retrieve.RetrieveStudentInfo
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

    single<ConnectorHttpClient> { KtorHttpClient() }
    single { CookieStep(get()) }
    single { LoginStep(get()) }
    single { LoginRedirectStep(get()) }
    single { DashboardStep(get()) }
    single { DashboardRedirectStep(get()) }


    single { CookieParser() }
    single { CookieExtractor(get(), get()) }
    single<SemesterResultsExtraction> { DefaultSemesterResultsExtraction(get(), get(), get(), get(), get()) }
    single { RetrieveSemesterResults(get()) }
}