package com.github.sua.usecase.retrieve.dto

sealed class RetrieveOutput<out T: Any> {

    data class RetrieveSuccess<out T: Any>(val data: T): RetrieveOutput<T>()

    data class RetrieveError(val message: String): RetrieveOutput<Nothing>()

}