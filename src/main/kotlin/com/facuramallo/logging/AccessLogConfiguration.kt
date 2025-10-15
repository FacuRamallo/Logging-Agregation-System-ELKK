package com.facuramallo.logging

import ch.qos.logback.access.tomcat.LogbackValve
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class AccessLogConfiguration {
    @Bean
    open fun logbackValveCustomizer(): WebServerFactoryCustomizer<TomcatServletWebServerFactory> {
        return WebServerFactoryCustomizer { factory ->
            val logbackValve = LogbackValve()
            logbackValve.setFilename("src/main/resources/logback-access.xml")
            factory.addContextValves(logbackValve)
        }
    }
}