package com.viartemev.tv.rest

import com.viartemev.tv.domain.Channel
import com.viartemev.tv.domain.Pageable
import com.viartemev.tv.service.TvService
import io.ktor.application.call
import io.ktor.locations.*
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing

@KtorExperimentalLocationsAPI
internal fun Routing.channels(tvService: TvService) {
    get<ChannelsLocation> { location ->
        //TODO move success response to feature
        call.respond(SuccessResp.of(tvService.getChannels(location.toPageable()).map { ChannelResponse.fromChannel(it) }))
    }
    post<ChannelsLocation> {
        //TODO jackson validation
        val channelRequest = call.receive<ChannelRequest>()
        call.respond(SuccessResp.of(ChannelResponse.fromChannel(tvService.storeChannel(channelRequest.toChannel()))))
    }
    put<ChannelsLocation.ChannelLocation> {
        val channelRequest = call.receive<ChannelRequest>()
        call.respond(SuccessResp.of(ChannelResponse.fromChannel(tvService.updateChannel(channelRequest.toChannel()))))
    }
    delete<ChannelsLocation.ChannelLocation> { ch ->
        call.respond(SuccessResp.of(tvService.deleteChannel(ch.id)))
    }
}

data class ChannelRequest(val id: Int?, val title: String, val icon: String, val archived: Boolean, val rank: Int?) {
    fun toChannel() = Channel(id, title, icon, archived, rank)
}

data class ChannelResponse(val id: Int, val title: String, val icon: String, val rank: Int) {
    companion object {
        fun fromChannel(channel: Channel) = with(channel) {
            ChannelResponse(id!!, title, icon, rank!!)
        }
    }
}

@KtorExperimentalLocationsAPI
@Location("/channels")
data class ChannelsLocation(val page: Int = 0, val size: Int = 20) {
    fun toPageable(): Pageable = Pageable(page = page, size = size)

    @Location("/{id}")
    data class ChannelLocation(val id: Int)
}