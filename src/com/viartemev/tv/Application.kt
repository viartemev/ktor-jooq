package com.viartemev.tv

import com.fasterxml.jackson.databind.SerializationFeature
import com.viartemev.tv.database.Database
import com.viartemev.tv.database.FlywayFeature
import com.viartemev.tv.rest.channels
import com.viartemev.tv.rest.jackson.customJackson
import com.viartemev.tv.rest.errors.statusPageConfiguration
import com.viartemev.tv.service.TvService
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.routing.Routing
import io.ktor.util.KtorExperimentalAPI

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val database = Database(this)
    val tvService = TvService(database)

    install(Locations)
    install(Routing) { channels(tvService) }
    install(StatusPages, statusPageConfiguration)
    install(FlywayFeature) { dataSource = database.connectionPool }
    install(ContentNegotiation) { customJackson { enable(SerializationFeature.INDENT_OUTPUT) } }
}