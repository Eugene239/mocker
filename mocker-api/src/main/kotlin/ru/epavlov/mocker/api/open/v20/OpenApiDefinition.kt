package ru.epavlov.mocker.api.open.v20

data class OpenApiDefinition(
    val type: OpenApiType,
    val properties: Map<String, OpenApiProperty>
)