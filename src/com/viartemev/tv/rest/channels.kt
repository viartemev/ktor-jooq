package com.viartemev.tv.rest

import com.viartemev.tv.service.TvService
import io.ktor.application.application
import io.ktor.application.call
import io.ktor.application.log
import io.ktor.request.receive
import io.ktor.request.receiveStream
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post

internal fun Routing.channels(tvService: TvService) {
    get("/channels") {
        //TODO error code
        val size = call.request.queryParameters["size"]?.toInt() ?: 20
        call.respond(SuccessResponse.of(tvService.getChannels(size)))
    }
    post("/channels") {
        val channelRequest = call.receive<ChannelRequest>()
        application.log.info(channelRequest.toString())
        call.respond("Done")
    }
}

data class ChannelRequest(val hello: String, val number: Int)
