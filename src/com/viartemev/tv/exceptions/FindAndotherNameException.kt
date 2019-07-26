package com.viartemev.tv.exceptions

import io.ktor.http.HttpStatusCode

open class FindAnotherNameException(
        val httpStatusCode: HttpStatusCode,
        val title: String,
        val details: String
) : RuntimeException()