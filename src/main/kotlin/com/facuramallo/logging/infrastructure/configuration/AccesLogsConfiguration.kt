package com.facuramallo.logging.infrastructure.configuration

import ch.qos.logback.access.tomcat.LogbackValve
import java.nio.file.Files
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AccesLogsConfiguration {

    @Bean
    fun addLogbackAccessValve() = TomcatContextCustomizer { context ->

        javaClass.getResourceAsStream("/logback-access-spring.xml").use {
            Files.createDirectories((context.catalinaBase.toPath()
                .resolve(LogbackValve.DEFAULT_CONFIG_FILE)).parent)

            if (it != null) {
                Files.copy(it, context.catalinaBase.toPath().resolve(LogbackValve.DEFAULT_CONFIG_FILE))
            }
        }

        LogbackValve().let {
            it.isQuiet = true
            context.pipeline.addValve(it)
        }
    }

}