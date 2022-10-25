package com.github.sua.usecase.retrieve

import com.github.sua.usecase.integration.dto.credential.SigaCredentialInput

class RetrieveStudentInfo {

    fun retrieve(input: SigaCredentialInput) {
        print("Input: $input")
    }

}