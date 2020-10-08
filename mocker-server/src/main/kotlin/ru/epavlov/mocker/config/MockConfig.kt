package ru.epavlov.mocker.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import java.util.*

@Component
@ConfigurationProperties(prefix = "mocker")
data class MockConfig(
        var methods: List<HttpMethod> = emptyList(),
        var maxDelay: Long = 5000L,
        var uuid: String = UUID.randomUUID().toString(),
        var pathRegex: String? = null,
        var locale: MockLocale? = null
)