package com.github.sua.utils

import java.util.Objects


object TestUtils {

    fun getFileContent(fileName: String): String {
        val clazzLoader = this::class.java.classLoader
        val stream = clazzLoader.getResourceAsStream(fileName)
        val reader = stream.bufferedReader()
        if (!Objects.isNull(reader)) {
            val data = reader.readText()
            reader.close()
            return data
        }
        throw RuntimeException("The requested file $fileName not found")
    }

}