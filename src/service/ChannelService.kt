package com.viartemev.service

import com.viartemev.database.Database
import com.viartemev.domain.Channel
import java.util.*

class ChannelService(private val database: Database) {

    fun getChannels(size: Int): List<Channel> {
        return (1..size).map { Channel(it.toLong(), UUID.randomUUID().toString()) }
    }
}
