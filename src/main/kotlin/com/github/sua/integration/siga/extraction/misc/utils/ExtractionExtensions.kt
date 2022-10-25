package com.github.sua.integration.siga.extraction.misc.utils

fun Map<String, String>.getIdentifiedBy(key: String): String {
    return this[key] ?: throw Exception("Invalid $key identified")
}