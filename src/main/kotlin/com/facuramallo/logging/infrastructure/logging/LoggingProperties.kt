package com.facuramallo.logging.infrastructure.logging

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "myapplication.logging")
class LoggingProperties{
    lateinit var allHeaders: Array<String>
}




