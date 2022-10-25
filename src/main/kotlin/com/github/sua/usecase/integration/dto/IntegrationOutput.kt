package com.github.sua.usecase.integration.dto

sealed class IntegrationOutput<out T : Any> {

    data class IntegrationSuccess<out T : Any>(val data: T) : IntegrationOutput<T>()

    data class IntegrationError(val message: String) : IntegrationOutput<Nothing>()

}