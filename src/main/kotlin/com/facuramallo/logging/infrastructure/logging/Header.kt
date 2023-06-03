package com.facuramallo.logging.infrastructure.logging

private const val KEY_PREFIX = "header-"
private const val VALUE_EMPTY = "-"

class Header(private val key: String, private val value: String?) {

    fun getKey() = KEY_PREFIX + key

    fun getValue() = if (value.isNullOrBlank()) VALUE_EMPTY else KEY_PREFIX + key
}