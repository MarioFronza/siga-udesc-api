package com.github.sua.extraction.dto


sealed class StepResponse {

    data class StepSuccess(val payload: String) : StepResponse()

    data class StepError(val payload: String) : StepResponse()

}