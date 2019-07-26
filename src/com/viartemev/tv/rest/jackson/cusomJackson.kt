package com.viartemev.tv.rest.jackson

import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.viartemev.tv.exceptions.BadRequestBodyException
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.features.ContentConverter
import io.ktor.features.ContentNegotiation
import io.ktor.features.suitableCharset
import io.ktor.http.ContentType
import io.ktor.http.content.TextContent
import io.ktor.http.withCharset
import io.ktor.request.ApplicationReceiveRequest
import io.ktor.request.contentCharset
import io.ktor.util.pipeline.PipelineContext
import kotlinx.coroutines.io.ByteReadChannel
import kotlinx.coroutines.io.jvm.javaio.toInputStream
import kotlinx.io.errors.IOException

fun ContentNegotiation.Configuration.customJackson(contentType: ContentType = ContentType.Application.Json,
                                                   block: ObjectMapper.() -> Unit = {}) {
    val mapper = jacksonObjectMapper()
    mapper.apply {
        setDefaultPrettyPrinter(DefaultPrettyPrinter().apply {
            indentArraysWith(DefaultPrettyPrinter.FixedSpaceIndenter.instance)
            indentObjectsWith(DefaultIndenter("  ", "\n"))
        })
    }
    mapper.apply(block)
    val converter = JacksonConverter(mapper)
    register(contentType, converter)
}

class JacksonConverter(private val objectmapper: ObjectMapper = jacksonObjectMapper()) : ContentConverter {
    override suspend fun convertForSend(context: PipelineContext<Any, ApplicationCall>, contentType: ContentType, value: Any): Any? {
        return TextContent(objectmapper.writeValueAsString(value), contentType.withCharset(context.call.suitableCharset()))
    }

    override suspend fun convertForReceive(context: PipelineContext<ApplicationReceiveRequest, ApplicationCall>): Any? {
        try {
            val request = context.subject
            val type = request.type
            val value = request.value as? ByteReadChannel ?: return null
            val reader = value.toInputStream().reader(context.call.request.contentCharset() ?: Charsets.UTF_8)
            return objectmapper.readValue(reader, type.javaObjectType)
        } catch (exception: IOException) {
            throw BadRequestBodyException(exception.localizedMessage)
        }
    }
}
