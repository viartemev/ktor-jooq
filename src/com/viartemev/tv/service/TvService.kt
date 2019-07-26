package com.viartemev.tv.service

import com.viartemev.tv.database.Database
import com.viartemev.tv.domain.Channel
import com.viartemev.tv.domain.Pageable
import io.ktor.util.KtorExperimentalAPI
import com.viartemev.tv.generated.tables.Channel.CHANNEL as ChannelTable

class TvService @KtorExperimentalAPI constructor(private val database: Database) {

    //TODO add seek in sql https://blog.jooq.org/2016/08/10/why-most-programmers-get-pagination-wrong/
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
            it.insertInto(ChannelTable, ChannelTable.TITLE, ChannelTable.LOGO, ChannelTable.RANK)
                    .values(channel.title, channel.logo, channel.rank)
                    .returning(ChannelTable.ID)
                    .fetchOne()
        }
        return channel.copy(id = id.getValue(ChannelTable.ID))
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
