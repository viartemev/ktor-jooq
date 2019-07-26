package com.viartemev.tv.rest.errors

import com.viartemev.tv.exceptions.BadRequestBodyException
import com.viartemev.tv.rest.ErrorResp
import io.ktor.application.application
import io.ktor.application.call
import io.ktor.application.log
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.util.error
import org.slf4j.Logger

val statusPageConfiguration: StatusPages.Configuration.() -> Unit = {
    exception<Throwable> { cause ->
        when (cause) {
            is BadRequestBodyException -> call.respond(ErrorResp.of(cause))
            else -> call.respond(HttpStatusCode.InternalServerError, unhandledError(application.log, cause))
        }
    }
}

private fun unhandledError(log: Logger, cause: Throwable): ErrorResp {
    log.error(cause)
    return ErrorResp.of(HttpStatusCode.InternalServerError, "Internal Server Error", "${cause.javaClass}: ${cause.localizedMessage}")
}