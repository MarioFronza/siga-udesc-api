package com.github.sua.http.controller

import com.github.sua.module
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals


class HealthCheckTest {

    @Test
    fun `test can execute health`() {
        withTestApplication(Application::module) {
            handleRequest(HttpMethod.Get, "/health").apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

}