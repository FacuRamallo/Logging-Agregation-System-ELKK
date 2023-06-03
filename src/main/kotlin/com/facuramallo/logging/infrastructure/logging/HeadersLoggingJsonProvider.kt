package com.facuramallo.logging.infrastructure.logging

import ch.qos.logback.access.spi.IAccessEvent
import com.fasterxml.jackson.core.JsonGenerator
import java.io.IOException
import net.logstash.logback.composite.AbstractJsonProvider


class HeadersLoggingJsonProvider(private var headers : Array<String>) : AbstractJsonProvider<IAccessEvent>() {

    fun setHeaders(headers: Array<String>) {
        val loggingProperties = LoggingProperties()
        loggingProperties.allHeaders = headers
        loggingProperties.allHeaders.also { this.headers = it }
    }

    @Throws(IOException::class)
    override fun writeTo(generator: JsonGenerator, event: IAccessEvent) {
        for (name in headers) {
            val header = Header(name, event.getRequestHeader(name))
            generator.writeStringField(header.getKey(), header.getValue())
        }
    }
}
