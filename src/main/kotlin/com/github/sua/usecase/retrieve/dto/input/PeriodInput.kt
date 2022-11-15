package com.github.sua.usecase.retrieve.dto.input

data class PeriodInput(
    val year: String,
    val term: Int,
){
    fun toKeyString() = "${year}/${term}"
}