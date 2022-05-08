package com.github.sua.usecase.dto.input.extraction

import com.github.sua.usecase.dto.input.credential.SigaCredentialInput

data class DashboardInput(
    val sigaCredential: SigaCredentialInput,
)