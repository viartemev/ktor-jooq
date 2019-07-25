package com.viartemev.database

import io.ktor.application.Application
import io.ktor.application.ApplicationFeature
import io.ktor.application.log
import io.ktor.config.ApplicationConfigurationException
import io.ktor.util.AttributeKey
import io.ktor.util.KtorExperimentalAPI
import org.flywaydb.core.Flyway
import javax.sql.DataSource

@KtorExperimentalAPI
class FlywayFeature(configuration: Configuration) {
    private val location = configuration.location
    private val dataSource = configuration.dataSource

    class Configuration {
        var location: String? = null
        var dataSource: DataSource? = null
    }

    companion object Feature : ApplicationFeature<Application, Configuration, FlywayFeature> {
        override val key = AttributeKey<FlywayFeature>("Flyway")

        override fun install(pipeline: Application, configure: Configuration.() -> Unit): FlywayFeature {
            val configuration = Configuration().apply(configure)
            val feature = FlywayFeature(configuration)
            pipeline.log.info("The migration has started...")

            val fluentConfiguration = Flyway.configure(pipeline.environment.classLoader)
            if (feature.location != null) fluentConfiguration.locations(feature.location)
            val load = fluentConfiguration
                .locations(feature.location ?: "db/migrations") //FIXME don't override default value
                .dataSource(
                    feature.dataSource ?: throw ApplicationConfigurationException("Data source is not configured")
                )
                .load()
            load.migrate()
            pipeline.log.info("Migration has finished...")
            return feature
        }
    }

}