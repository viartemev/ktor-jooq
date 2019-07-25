package com.viartemev.ktor_jooq.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.Application
import io.ktor.config.ApplicationConfig
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.future.await
import kotlinx.coroutines.withContext
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL

class Database(application: Application) {
    val connectionPool: HikariDataSource

    init {
        val databaseConfig = DatabaseConfig(application.environment.config)
        connectionPool = HikariDataSource(HikariConfig()
            .apply {
                jdbcUrl = databaseConfig.url
                username = databaseConfig.username
                maximumPoolSize = databaseConfig.poolSize
                password = databaseConfig.password
            }
            .also { it.validate() })
    }

    suspend fun <T> inTransaction(block: (DSLContext) -> T): T = withContext(Dispatchers.IO) {
        DSL.using(connectionPool, SQLDialect.POSTGRES)
            .transactionResultAsync { config -> block(DSL.using(config)) }.await()
    }
}

internal class DatabaseConfig @KtorExperimentalAPI constructor(applicationConfig: ApplicationConfig) {
    private val config = applicationConfig.config("database")
    val url = config.property("connection").getString()
    val poolSize = config.property("poolSize").getString().toInt()
    val password = config.property("password").getString()
    val username = config.property("username").getString()
}