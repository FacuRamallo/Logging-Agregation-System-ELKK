package com.facuramallo.logging

import io.restassured.module.mockmvc.RestAssuredMockMvc.given
import io.restassured.module.mockmvc.RestAssuredMockMvc.mockMvc
import java.util.function.Consumer
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.system.CapturedOutput
import org.springframework.boot.test.system.OutputCaptureExtension
import org.springframework.http.HttpStatus.OK
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest(
    classes = [LoggingApplication::class],
    properties = ["spring.profiles.active=k8s"]
)
@AutoConfigureMockMvc
@ExtendWith(OutputCaptureExtension::class)
class LoggingTestCase {

    @Autowired
    lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setup() = mockMvc(mockMvc)

    @Test
    fun `should log acces-log`(output: CapturedOutput) {
        given()
            .get("/log")
            .then()
            .status(OK)
            .body(equalTo("logs should be as wanted"))

        assertTrue { output.out.contains("loggin a log from ") }

    }
}