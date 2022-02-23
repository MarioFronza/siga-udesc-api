package com.github.sua.http.plugins

import com.github.sua.extraction.DefaultSemesterResultsExtraction
import com.github.sua.extraction.extractor.cookie.CookieExtractor
import com.github.sua.extraction.misc.httpclient.ExtractorHttpClient
import com.github.sua.extraction.misc.httpclient.ktor.KtorHttpClient
import com.github.sua.extraction.parser.cookie.CookieParser
import com.github.sua.extraction.step.login.CookieStep
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

    single<ExtractorHttpClient> { KtorHttpClient() }
    single { CookieStep(get()) }
    single { CookieParser() }
    single { CookieExtractor(get(), get()) }
    single<SemesterResultsExtraction> { DefaultSemesterResultsExtraction(get()) }
    single { RetrieveSemesterResults(get()) }
}