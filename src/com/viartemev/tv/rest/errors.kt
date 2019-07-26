package com.viartemev.tv.rest

import io.ktor.application.application
import io.ktor.application.call
import io.ktor.application.log
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.util.error
import org.slf4j.Logger

val statusPageConfiguration: StatusPages.Configuration.() -> Unit = {
    exception<Throwable> {
        when {
            else -> call.respond(HttpStatusCode.InternalServerError, unhandledError(application.log, it))
        }
    }
}

private fun unhandledError(log: Logger, cause: Throwable): ErrorResp {
    log.error(cause)
    return ErrorResp.of(HttpStatusCode.InternalServerError, "Server failed", "${cause.javaClass}: ${cause.localizedMessage}")
}