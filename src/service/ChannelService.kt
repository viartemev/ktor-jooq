package com.viartemev.service

import com.viartemev.database.Database
import com.viartemev.domain.Channel
import io.ktor.util.KtorExperimentalAPI
import java.util.*

class ChannelService @KtorExperimentalAPI constructor(private val database: Database) {

    suspend fun getChannels(size: Int): List<Channel> {
        val inTransaction = database.inTransaction { dslContext ->
            dslContext.select().from("car").limit(size).fetchArray()
        }
        println(inTransaction)
        return (1..size).map { Channel(it.toLong(), UUID.randomUUID().toString()) }
    }
}
