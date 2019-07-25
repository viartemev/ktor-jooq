package com.viartemev.ktor_jooq.service

import com.viartemev.ktor_jooq.database.Database
import com.viartemev.ktor_jooq.domain.Channel
import com.viartemev.ktor_jooq.generated.tables.Channels.CHANNELS
import io.ktor.util.KtorExperimentalAPI

class ChannelService @KtorExperimentalAPI constructor(private val database: Database) {

    suspend fun getChannels(size: Int): List<Channel> {
        val channel = database.inTransaction { dslContext ->
            dslContext.insertInto(CHANNELS, CHANNELS.ID, CHANNELS.NAME).values(4, "four").execute()
            dslContext.select().from(CHANNELS).where(CHANNELS.ID.eq(4)).fetchOneInto(Channel::class.java)
        }
        return (1..size).map { channel }
    }
}
