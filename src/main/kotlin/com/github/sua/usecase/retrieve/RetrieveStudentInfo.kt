package com.github.sua.usecase.retrieve

import com.github.sua.usecase.retrieve.dto.input.SigaCredentialInput

class RetrieveStudentInfo {

    fun retrieve(input: SigaCredentialInput) {
        print("Input: $input")
    }

}