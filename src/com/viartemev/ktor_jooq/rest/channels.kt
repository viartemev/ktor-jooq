package com.viartemev.ktor_jooq.rest

import com.viartemev.ktor_jooq.service.ChannelService
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get


internal fun Routing.channels(channelService: ChannelService) {
    get("/channels") {
        //TODO error code
        val size = call.request.queryParameters["size"]?.toInt() ?: 20
        call.respond(Response.successOf(channelService.getChannels(size)))
    }
}