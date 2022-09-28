package com.github.sua.http.plugins

import com.github.sua.extraction.DashboardExtraction
import com.github.sua.extraction.DefaultPartialResultsExtraction
import com.github.sua.extraction.DefaultSemesterResultsExtraction
import com.github.sua.extraction.extractor.dashboard.DashboardExtractor
import com.github.sua.extraction.extractor.dashboard.DashboardRedirectExtractor
import com.github.sua.extraction.extractor.login.CookieExtractor
import com.github.sua.extraction.extractor.login.LoginExtractor
import com.github.sua.extraction.extractor.login.LoginRedirectExtractor
import com.github.sua.extraction.extractor.results.partial.DashboardPartialResultsExtractor
import com.github.sua.extraction.extractor.results.partial.PartialResultsByPeriodExtractor
import com.github.sua.extraction.extractor.results.partial.PartialResultsExtractor
import com.github.sua.extraction.extractor.results.semester.DashboardSemesterResultsExtractor
import com.github.sua.extraction.extractor.results.semester.SemesterResultsByPeriodExtractor
import com.github.sua.extraction.extractor.results.semester.SemesterResultsExtractor
import com.github.sua.extraction.misc.httpclient.ConnectorHttpClient
import com.github.sua.extraction.misc.httpclient.ktor.KtorHttpClient
import com.github.sua.extraction.parser.cookie.CookieParser
import com.github.sua.extraction.parser.dashboard.DashboardParser
import com.github.sua.extraction.parser.results.PartialResultsParser
import com.github.sua.extraction.parser.results.SemesterResultsParser
import com.github.sua.extraction.step.dashboard.DashboardRedirectStep
import com.github.sua.extraction.step.dashboard.DashboardStep
import com.github.sua.extraction.step.login.CookieStep
import com.github.sua.extraction.step.login.LoginRedirectStep
import com.github.sua.extraction.step.login.LoginStep
import com.github.sua.extraction.step.results.partial.DashboardPartialResultsStep
import com.github.sua.extraction.step.results.partial.PartialResultsByPeriodStep
import com.github.sua.extraction.step.results.partial.PartialResultsStep
import com.github.sua.extraction.step.results.semester.DashboardSemesterResultsStep
import com.github.sua.extraction.step.results.semester.SemesterResultsByPeriodStep
import com.github.sua.extraction.step.results.semester.SemesterResultsStep
import com.github.sua.usecase.extraction.PartialResultsExtraction
import com.github.sua.usecase.extraction.SemesterResultsExtraction
import com.github.sua.usecase.retrieve.RetrievePartialResults
import com.github.sua.usecase.retrieve.RetrieveSemesterResults
import com.github.sua.usecase.retrieve.RetrieveStudentInfo
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.dsl.single
import org.koin.ktor.plugin.Koin

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
    single { SemesterResultsByPeriodStep(get()) }
    single { PartialResultsByPeriodStep(get()) }
    single { DashboardSemesterResultsStep(get()) }
    single { DashboardPartialResultsStep(get()) }
    single { SemesterResultsStep(get()) }
    single { PartialResultsStep(get()) }

    single { CookieParser() }
    single { DashboardParser() }
    single { SemesterResultsParser() }
    single { PartialResultsParser() }

    single { CookieExtractor(get(), get()) }
    single { LoginExtractor(get()) }
    single { LoginRedirectExtractor(get()) }
    single { DashboardExtractor(get(), get()) }
    single { SemesterResultsExtractor(get(), get()) }
    single { PartialResultsExtractor(get(), get()) }
    single { DashboardRedirectExtractor(get()) }
    single { DashboardSemesterResultsExtractor(get(), get()) }
    single { DashboardPartialResultsExtractor(get(), get()) }
    single { SemesterResultsByPeriodExtractor(get(), get()) }
    single { PartialResultsByPeriodExtractor(get(), get()) }
    single { DashboardExtraction(get(), get(), get(), get(), get()) }
    single<SemesterResultsExtraction> {
        DefaultSemesterResultsExtraction(get(), get(), get(), get())
    }
    single<PartialResultsExtraction> {
        DefaultPartialResultsExtraction(get(), get(), get(), get())
    }
    single { RetrieveSemesterResults(get()) }
    single { RetrievePartialResults(get()) }
}