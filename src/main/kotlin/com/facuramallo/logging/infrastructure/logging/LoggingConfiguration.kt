package com.facuramallo.logging.infrastructure.logging

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@ConfigurationProperties(prefix = "myapllication.logging")
@EnableConfigurationProperties(LoggingProperties::class)
class LoggingConfiguration {
    companion object {
        @Bean
        fun headersLoggingFilter(loggingProperties: LoggingProperties): HeadersLoggingFilter {
            return HeadersLoggingFilter(loggingProperties.allHeaders)
        }
    }

}