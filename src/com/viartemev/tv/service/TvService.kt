package com.viartemev.tv.service

import com.viartemev.tv.database.Database
import com.viartemev.tv.domain.Channel
import com.viartemev.tv.domain.Pageable
import io.ktor.util.KtorExperimentalAPI
import com.viartemev.tv.generated.tables.Channel as ChannelTable

class TvService @KtorExperimentalAPI constructor(private val database: Database) {

    suspend fun getChannels(pageable: Pageable): List<Channel> {
        val channel = database.inTransaction { dslContext ->
            dslContext.select().from(ChannelTable.CHANNEL).where(ChannelTable.CHANNEL.ID.eq(4))
                    .fetchOneInto(Channel::class.java)
        }
        return (1..pageable.size).map { channel }
    }

    fun storeChannel(channel: Channel): Channel {
        println("Channel has stored $channel")
        return channel
    }

    fun updateChannel(channel: Channel): Channel {
        println("Channel has updated $channel")
        return channel
    }

    fun deleteChannel(id: Int): Boolean {
        println("Channel has deleted $id")
        return true
    }
}
