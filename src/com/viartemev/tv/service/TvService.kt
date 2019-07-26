package com.viartemev.tv.service

import com.viartemev.tv.database.Database
import com.viartemev.tv.domain.Channel
import io.ktor.util.KtorExperimentalAPI
import com.viartemev.tv.generated.tables.Channel as ChannelTable

class TvService @KtorExperimentalAPI constructor(private val database: Database) {

    suspend fun getChannels(size: Int): List<Channel> {
        val channel = database.inTransaction { dslContext ->
            dslContext.select().from(ChannelTable.CHANNEL).where(ChannelTable.CHANNEL.ID.eq(4))
                    .fetchOneInto(Channel::class.java)
        }
        return (1..size).map { channel }
    }
}
