package com.github.sua.integration.siga

import com.github.sua.integration.siga.extraction.DashboardExtraction
import com.github.sua.integration.siga.extraction.extractor.results.semester.DashboardSemesterResultsExtractor
import com.github.sua.integration.siga.extraction.extractor.results.semester.SemesterResultsExtractor
import com.github.sua.integration.siga.parser.dashboard.DashboardParser.Companion.SEMESTER_RESULT_PAGE
import com.github.sua.integration.siga.extraction.dto.DashboardInput.Companion.fromSigaCredentialInput
import com.github.sua.integration.siga.extraction.extractor.results.partial.DashboardPartialResultsExtractor
import com.github.sua.integration.siga.extraction.extractor.results.partial.PartialResultsByPeriodExtractor
import com.github.sua.integration.siga.extraction.extractor.results.partial.PartialResultsExtractor
import com.github.sua.integration.siga.extraction.extractor.results.semester.SemesterResultsByPeriodExtractor
import com.github.sua.integration.siga.extraction.misc.utils.getIdentifiedBy
import com.github.sua.integration.siga.parser.dashboard.DashboardParser.Companion.PARTIAL_RESULT_PAGE
import com.github.sua.usecase.integration.StudentInfoIntegration
import com.github.sua.usecase.integration.dto.IntegrationOutput
import com.github.sua.usecase.integration.dto.IntegrationOutput.IntegrationSuccess
import com.github.sua.usecase.integration.dto.IntegrationOutput.IntegrationError
import com.github.sua.usecase.retrieve.dto.input.SigaCredentialInput
import com.github.sua.usecase.retrieve.dto.output.StudentInfoOutput

class DefaultStudentInfoIntegration(
    private val dashboardExtraction: DashboardExtraction,
    private val dashboardSemesterResultsExtractor: DashboardSemesterResultsExtractor,
    private val semesterResultsExtractor: SemesterResultsExtractor,
    private val semesterResultsByPeriodExtractor: SemesterResultsByPeriodExtractor,
    private val partialResultsExtractor: PartialResultsExtractor,
    private val dashboardPartialResultsExtractor: DashboardPartialResultsExtractor,
    private val partialResultsByPeriodExtractor: PartialResultsByPeriodExtractor
) : StudentInfoIntegration {

    override fun integrate(input: SigaCredentialInput): IntegrationOutput<StudentInfoOutput> {
        try {
            val dashboardExtractionResponse = dashboardExtraction.extract(
                input = fromSigaCredentialInput(input),
                resultPageType = SEMESTER_RESULT_PAGE
            )

            val dashboardSemesterResultsExtractorResponse = dashboardSemesterResultsExtractor.extract(
                request = dashboardExtractionResponse.toDashboardSemesterResultsExtractorRequest()
            )

            val semesterResultsResponse = semesterResultsExtractor.extract(
                request = dashboardSemesterResultsExtractorResponse.toSemesterResultsExtractorRequest(
                    sessionId = dashboardExtractionResponse.cookieExtractorResponseSessionId,
                    etts = dashboardExtractionResponse.loginRedirectExtractorResponseEtts
                )
            )

            val studentSemesterResults = semesterResultsResponse.periodsIdentified.map { periodIdentified ->
                semesterResultsByPeriodExtractor.extract(
                    request = semesterResultsResponse.toSemesterResultsByPeriodExtractorRequest(
                        etts = dashboardExtractionResponse.loginRedirectExtractorResponseEtts,
                        periodIdentified = periodIdentified.value
                    )
                )
            }

            val partialResults = semesterResultsResponse.periodsIdentified.flatMap { periodIdentified ->
                semesterResultsByPeriodExtractor.extract(
                    request = semesterResultsResponse.toSemesterResultsByPeriodExtractorRequest(
                        etts = dashboardExtractionResponse.loginRedirectExtractorResponseEtts,
                        periodIdentified = periodIdentified.value
                    )
                )

                val dashboardPartialResultsExtractionResponse = dashboardExtraction.extract(
                    input = fromSigaCredentialInput(input),
                    resultPageType = PARTIAL_RESULT_PAGE
                )

                val dashboardPartialResultsExtractorResponse = dashboardPartialResultsExtractor.extract(
                    request = dashboardPartialResultsExtractionResponse.toDashboardPartialResultsExtractorRequest()
                )

                val partialResultsResponse = partialResultsExtractor.extract(
                    request = dashboardPartialResultsExtractorResponse.toPartialResultsExtractorRequest(
                        sessionId = dashboardPartialResultsExtractionResponse.cookieExtractorResponseSessionId,
                        etts = dashboardPartialResultsExtractionResponse.loginRedirectExtractorResponseEtts
                    )
                )

                partialResultsResponse.subjectsIdentified.map { subjectIdentified ->
                    partialResultsByPeriodExtractor.extract(
                        request = partialResultsResponse.toPartialResultsByPeriodExtractorRequest(
                            etts = dashboardPartialResultsExtractionResponse.loginRedirectExtractorResponseEtts,
                            periodIdentified = periodIdentified.value,
                            courseIdentified = partialResultsResponse.coursesIdentified.getIdentifiedBy(key = "Engenharia de Software"),
                            subjectIdentified = subjectIdentified.value
                        )
                    )
                }
            }

            return IntegrationSuccess(
                data = StudentInfoOutput(
                    studentName = dashboardExtractionResponse.studentName,
                    semesterResults = studentSemesterResults.map { studentSemesterResult ->
                        val partialResultsByPeriod = partialResults.filter { result ->
                            result.partialResults.period == studentSemesterResult.semesterResults.period
                        }
                        val semesterResultsByPeriod =
                            studentSemesterResult.semesterResults.semesterResults.map { result ->
                                result.toStudentInfoSemesterResult(partialResultsByPeriod)
                            }
                        studentSemesterResult.semesterResults.toStudentInfoSemesterResults(semesterResultsByPeriod)
                    }
                )
            )
        } catch (e: Exception) {
            return IntegrationError(
                message = "Student info integration unexpected error: ${e.message}"
            )
        }
    }

}

