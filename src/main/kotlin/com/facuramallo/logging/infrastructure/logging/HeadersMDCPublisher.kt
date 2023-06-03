package com.facuramallo.logging.infrastructure.logging

import java.io.Closeable
import javax.servlet.http.HttpServletRequest
import org.slf4j.MDC

class HeadersMDCPublisher(private val headers: Array<String>) {

    private companion object MDCCloseable : Closeable {

        private val closeables = mutableListOf<MDC.MDCCloseable>()

        fun add(closeable: MDC.MDCCloseable) {
            closeables.add(closeable)
        }

        override fun close() {
            closeables.forEach { it.close() }
        }
    }

    fun publish(request: HttpServletRequest, runnable: MDCRunnable) {
        publishCloseable(request).use {  runnable.run() }
    }


    private fun publishCloseable(request: HttpServletRequest): Closeable {
        val closeables = MDCCloseable
        headers
            .map { Header(it, request.getHeader(it)) }
            .map { MDC.putCloseable(it.getKey(), it.getValue()) }
            .forEach { closeables.add(it) }
        return closeables
    }
}


fun interface MDCRunnable {
    fun run()
}


