package ru.epavlov.mocker.api.open.v20

import ru.epavlov.mocker.api.common.APPLICATION_JSON

data class OpenApiMethod(
    val operationId: String?,
    val description: String?,
    val summary: String?,
    val consumes: List<String>? = listOf(APPLICATION_JSON),
    val produces: List<String>? = listOf(APPLICATION_JSON),
    val parameters: List<OpenApiParameter>? = emptyList(),
    val deprecated: Boolean? = false
)
