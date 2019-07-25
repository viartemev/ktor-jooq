package com.viartemev

import com.fasterxml.jackson.databind.SerializationFeature
import com.viartemev.database.Database
import com.viartemev.database.FlywayFeature
import com.viartemev.rest.channels
import com.viartemev.service.ChannelService
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.util.KtorExperimentalAPI

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalAPI
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val database = Database(this)
    val channelService = ChannelService(database)

    install(FlywayFeature) {
        dataSource = database.connectionPool
    }
    install(ContentNegotiation) { jackson { enable(SerializationFeature.INDENT_OUTPUT) } }
    install(Routing) { channels(channelService) }

    //TODO create pattern for error response
    install(StatusPages) {
        exception<Throwable> { cause ->
            call.respond(
                HttpStatusCode.InternalServerError,
                "${cause.javaClass}: ${cause.localizedMessage}"
            )
        }
    }
}

