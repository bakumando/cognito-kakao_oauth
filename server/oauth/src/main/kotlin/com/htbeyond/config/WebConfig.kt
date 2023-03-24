package com.htbeyond.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.feature
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.forms.formData
import io.ktor.features.ContentNegotiation

@Configuration
class WebConfig(
    private val objectMapper: ObjectMapper,
) {

    @Bean
    fun httpClient(): HttpClient = HttpClient(Apache) {

        install(JsonFeature) {
            serializer = JacksonSerializer(objectMapper)
        }

        install(HttpTimeout) {
            socketTimeoutMillis = 10_000
            requestTimeoutMillis = HttpTimeout.INFINITE_TIMEOUT_MS
            connectTimeoutMillis = 10_000
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }
}