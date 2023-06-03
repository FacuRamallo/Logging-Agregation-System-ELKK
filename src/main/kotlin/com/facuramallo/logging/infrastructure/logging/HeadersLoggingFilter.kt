package com.facuramallo.logging.infrastructure.logging

import java.io.IOException
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest


class HeadersLoggingFilter(headers: Array<String>) : Filter {

    private  var headersMDCPublisher : HeadersMDCPublisher

    init {
        headersMDCPublisher = HeadersMDCPublisher(headers)
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        if ( request !is HttpServletRequest) chain.doFilter(request, response)
        doFilterHttp(request as HttpServletRequest, response, chain)
    }

    private fun doFilterHttp(request: HttpServletRequest, response: ServletResponse, chain: FilterChain) {
        try {
            headersMDCPublisher.publish(request) { chain.doFilter(request, response) }
        } catch (e: IOException) {
            throw e
        } catch (e: ServletException) {
            throw e
        } catch (e: RuntimeException) {
            throw e
        } catch (e: Throwable) {
            throw RuntimeException(e)
        }
    }

}
