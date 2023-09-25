package com.facuramallo.logging

import kotlin.reflect.jvm.jvmName
import org.slf4j.LoggerFactory.getLogger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GetController {
    companion object {
        private val logger = getLogger(GetController::class.java)
    }

    @GetMapping("/log")
    fun doSomething(): ResponseEntity<String> {
        val response = "logs should be as wanted"
        logger.info("loggin an info log from {}", GetController::class.jvmName)
        logger.warn("loggin a warn log from {}", GetController::class.jvmName)
        logger.error("loggin a error log from {}", GetController::class.jvmName)
        logger.debug("loggin a debug log from {}", GetController::class.jvmName)
        return ResponseEntity.ok(response)
    }
}