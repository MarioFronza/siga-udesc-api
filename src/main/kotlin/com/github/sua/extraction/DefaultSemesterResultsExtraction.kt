package com.github.sua.extraction

import com.github.sua.extraction.extractor.cookie.CookieExtractor
import com.github.sua.usecase.dto.input.SemesterResultsInput
import com.github.sua.usecase.extraction.SemesterResultsExtraction

class DefaultSemesterResultsExtraction(
    private val cookieExtractor: CookieExtractor
) : SemesterResultsExtraction {

    override fun extract(input: SemesterResultsInput) {
        val url = cookieExtractor.extract()
        print(url)
    }

}