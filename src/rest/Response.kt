package com.viartemev.rest

class Response<T> private constructor(val result: T) {
    companion object {
        fun <T> successOf(data: T) = Response(data)
    }
}