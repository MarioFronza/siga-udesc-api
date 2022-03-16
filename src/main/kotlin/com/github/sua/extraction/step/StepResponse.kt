package com.github.sua.extraction.step

import com.github.sua.extraction.parser.ExtractorParser


sealed class StepResponse {

    data class StepSuccess(val payload: String, val headers: Map<String, List<String>>) : StepResponse() {
        private val extractorParser by lazy { ExtractorParser() }

        fun getSessionId(position: Int = 1) = getHeaderByPosition(headers, position)

        fun getEtts(position: Int = 0) = getHeaderByPosition(headers, position)

        fun getViewState() = extractorParser.parseViewState(payload)

        fun getEndpoint() = extractorParser.parseEndpoint(payload)

        private fun getHeaderByPosition(headers: Map<String, List<String>>, position: Int): String {
            val header =
                headers[COOKIE_HEADER_KEY]?.filterIndexed { index, _ -> index == position } ?: throw Exception("oi")
            return header.first().split(";").first()
        }
    }

    data class StepError(val payload: String) : StepResponse()


    companion object {
        const val COOKIE_HEADER_KEY = "Set-Cookie"
    }
}