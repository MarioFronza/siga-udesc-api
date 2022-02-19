package com.github.sua.usecase

import com.github.sua.usecase.dto.input.StudentInfoInput

class RetrieveStudentInfo {

    fun retrieve(input: StudentInfoInput) {
        print("Input: $input")
    }

}