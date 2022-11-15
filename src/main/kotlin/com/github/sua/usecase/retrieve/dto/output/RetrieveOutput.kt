package com.github.sua.usecase.retrieve.dto.output

import com.github.sua.usecase.integration.dto.IntegrationOutput
import com.github.sua.usecase.integration.dto.IntegrationOutput.IntegrationSuccess
import com.github.sua.usecase.integration.dto.IntegrationOutput.IntegrationError

sealed class RetrieveOutput<out T : Any> {

    data class RetrieveSuccess<out T : Any>(val data: T) : RetrieveOutput<T>()

    data class RetrieveError(val message: String) : RetrieveOutput<Nothing>()

    fun getOutputContent() = when (this) {
        is RetrieveSuccess -> data
        is RetrieveError -> message
    }

    companion object {
        fun <T : Any> createOutputFromIntegrationResponse(integrationResponse: IntegrationOutput<T>) =
            when (integrationResponse) {
                is IntegrationSuccess -> RetrieveSuccess(integrationResponse.data)
                is IntegrationError -> RetrieveError(integrationResponse.message)
            }

    }
}