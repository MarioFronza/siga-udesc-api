package com.github.sua.entity.siga

import com.github.sua.entity.student.Student

data class SemesterResult(
    val finalGrade: Double,
    val courseLoad: Int,
    val absencesCount: Int,
    val attendancePercentage: Double,
    val result: String,
    val student: Student,
    val subjectName: String
)
