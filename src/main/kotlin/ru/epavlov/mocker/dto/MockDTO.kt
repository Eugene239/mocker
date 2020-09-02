package ru.epavlov.mocker.dto

import org.springframework.http.HttpMethod
import java.time.OffsetDateTime

data class MockDTO(
        val id: Long? = null,
        val path: String,
        val method: HttpMethod,
        var params: List<ParamsDTO> = emptyList(),
        val created: OffsetDateTime = OffsetDateTime.now()
)