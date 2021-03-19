package ru.epavlov.mocker.api.dto

import java.time.OffsetDateTime

data class MockDTO(
    val id: Long? = null,
    val path: String,
    val method: String,
    var params: List<ParamsDTO> = emptyList(),
    val created: OffsetDateTime = OffsetDateTime.now()
)