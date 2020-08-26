package ru.epavlov.mocker.dto

import org.springframework.http.HttpMethod
import java.time.OffsetDateTime

data class MockDTO(
        val id: Long? = null,
        val path: String? = null,
        val method: HttpMethod? = null,
        var params: List<ParamsDTO>? = null,
        val created: OffsetDateTime? = null
)