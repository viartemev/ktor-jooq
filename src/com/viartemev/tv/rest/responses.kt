package com.viartemev.tv.rest

import io.ktor.http.HttpStatusCode

class SuccessResponse<T> private constructor(val result: T) {
    companion object {
        fun <T> of(data: T) = SuccessResponse(data)
    }
}

class ErrorResponse private constructor(val status: Int, val title: String, val details: String) {
    companion object {
        fun of(code: HttpStatusCode, title: String, details: String) = ErrorResponse(code.value, title, details)
    }
}