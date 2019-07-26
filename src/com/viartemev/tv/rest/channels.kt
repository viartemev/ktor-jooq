package com.viartemev.tv.rest

import com.viartemev.tv.service.TvService
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get

internal fun Routing.channels(tvService: TvService) {
    get("/channels") {
        //TODO error code
        val size = call.request.queryParameters["size"]?.toInt() ?: 20
        call.respond(Response.successOf(tvService.getChannels(size)))
    }
}