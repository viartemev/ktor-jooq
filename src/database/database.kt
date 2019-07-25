package com.viartemev.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.Application
import io.ktor.config.ApplicationConfig
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalAPI
class Database(application: Application) {
    private val connectionPool: HikariDataSource

    init {
        val databaseConfig = DatabaseConfig(application.environment.config)
        connectionPool = HikariDataSource(HikariConfig()
            .apply {
                username = databaseConfig.username
                maximumPoolSize = databaseConfig.poolSize
                password = databaseConfig.password
            }
            .also { it.validate() })
    }
}

@KtorExperimentalAPI
internal class DatabaseConfig constructor(applicationConfig: ApplicationConfig) {
    private val config = applicationConfig.config("database")
    val url = config.property("connection").getString()
    val poolSize = config.property("poolSize").getString().toInt()
    val password = config.property("password").getString()
    val username = config.property("username").getString()
}