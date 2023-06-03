package com.facuramallo.logging

import org.slf4j.LoggerFactory.getLogger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GetController {

    private val logger = getLogger(this::class.java)


    @GetMapping("/log")
    fun doSomething(): ResponseEntity<String> {
        val response = "logs should be as wanted"
        logger.info("loggin a log from {}", GetController::class.simpleName)
        return ResponseEntity.ok(response)
    }
}