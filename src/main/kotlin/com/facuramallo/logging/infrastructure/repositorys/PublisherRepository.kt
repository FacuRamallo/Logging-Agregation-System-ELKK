package com.facuramallo.logging.infrastructure.repositorys

import com.facuramallo.logging.infrastructure.logging.ApiKey
import java.util.UUID
import java.util.UUID.fromString
import org.springframework.stereotype.Component

@Component
class PublisherRepository {
    private val publishers: MutableMap<ApiKey, UUID> = mutableMapOf()
    init {
        publishers.put(ApiKey("ce829e2affa641bbb0ccaea81b029c0c"), fromString("03eea678-8558-4ad1-a1da-5d33834ecef7"))
    }

    fun getPublisherIdFrom(apiKey: ApiKey) = publishers.get(apiKey)

}