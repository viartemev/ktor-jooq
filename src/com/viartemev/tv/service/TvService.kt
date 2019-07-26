package com.viartemev.tv.service

import com.viartemev.tv.database.Database
import com.viartemev.tv.domain.Channel
import com.viartemev.tv.domain.Pageable
import io.ktor.util.KtorExperimentalAPI
import com.viartemev.tv.generated.tables.Channel.CHANNEL as ChannelTable

class TvService @KtorExperimentalAPI constructor(private val database: Database) {

    suspend fun getChannels(pageable: Pageable): List<Channel> {
        return database.query {
            it.select()
                .from(ChannelTable)
                .orderBy(ChannelTable.RANK.desc())
                .offset(pageable.size * pageable.page)
                .limit(pageable.size)
                .fetchInto(Channel::class.java)
        }
    }

    suspend fun storeChannel(channel: Channel): Channel {
        val id = database.write {
            it.newRecord(ChannelTable)
                .apply {
                    title = channel.title
                    logo = channel.logo
                    rank = channel.rank
                }
                .store()
        }
        return channel.copy(id = id)
    }

    fun updateChannel(channel: Channel): Channel {

        println("Channel has updated $channel")
        return channel
    }

    suspend fun deleteChannel(id: Int): Boolean {
        val deleted = database.write {
            it.deleteFrom(ChannelTable)
                .where(ChannelTable.ID.eq(id))
                .execute()
        }
        return deleted == 1
    }
}
