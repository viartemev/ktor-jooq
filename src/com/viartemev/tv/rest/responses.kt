package com.viartemev.tv.rest

import com.viartemev.tv.exceptions.FindAnotherNameException
import io.ktor.http.HttpStatusCode

class SuccessResp<T> private constructor(val result: T) {
    companion object {
        fun <T> of(data: T) = SuccessResp(data)
    }
}

class ErrorResp private constructor(val status: Int, val title: String, val details: String) {
    companion object {
        fun of(code: HttpStatusCode, title: String, details: String) = ErrorResp(code.value, title, details)
        fun of(exception: FindAnotherNameException) = with(exception) { ErrorResp(httpStatusCode.value, title, details) }
    }
}