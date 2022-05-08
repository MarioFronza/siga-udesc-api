package com.github.sua.usecase.retrieve

import com.github.sua.usecase.dto.input.credential.SigaCredentialInput

class RetrieveStudentInfo {

    fun retrieve(input: SigaCredentialInput) {
        print("Input: $input")
    }

}