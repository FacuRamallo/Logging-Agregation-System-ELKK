package com.facuramallo.logging.infrastructure.logging

import com.facuramallo.logging.infrastructure.repositorys.PublisherRepository
import java.io.IOException
import java.util.Collections
import java.util.Enumeration
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper
import org.slf4j.LoggerFactory


private const val PUBLISHER_ID_HEADER = "PublisherId"
class ApikeyHeaderFilter(
    private val publisherRepository: PublisherRepository
) : Filter {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(this::class.java)
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        if ( request !is HttpServletRequest) chain.doFilter(request, response)
        doFilterHttp(request as HttpServletRequest, response, chain)
    }

    private fun doFilterHttp(request: HttpServletRequest, response: ServletResponse, chain: FilterChain) {
        LOGGER.info(request.getHeader("Inmofactory-Api-Key"))
        val wrapedRequest = HeaderMapRequestWrapper(request)
        val apiKey = wrapedRequest.getHeader("Inmofactory-Api-Key")?.trim()?.let { ApiKey(it) }
        try {
            if (apiKey != null) {
                publisherRepository.getPublisherIdFrom(apiKey)?.let {
                    wrapedRequest.addHeader(PUBLISHER_ID_HEADER, it.toString())
                    chain.doFilter(wrapedRequest,response)
                }
            }
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

data class ApiKey(val it: String) 

class HeaderMapRequestWrapper(request: HttpServletRequest) : HttpServletRequestWrapper(request) {
    private var headerMap = mutableMapOf<String,String>()

    fun addHeader(name: String, value: String) {
        headerMap[name] = value
    }

    override fun getHeader(name: String): String? {
        var headerValue = super.getHeader(name)
        if (headerMap.containsKey(name)) headerValue = headerMap.getValue(name)
        return headerValue
    }

    override fun getHeaders(name: String): Enumeration<String> {
        var values = Collections.list(super.getHeaders(name))
        if (headerMap.containsKey(name)) { values.add(headerMap[name]) }
        return Collections.enumeration(values)
    }

    override fun getHeaderNames(): Enumeration<String> {
        var names = Collections.list(super.getHeaderNames())
        for (name in headerMap.keys) {
            names.add(name)
        }
        return Collections.enumeration(names)
    }
}

