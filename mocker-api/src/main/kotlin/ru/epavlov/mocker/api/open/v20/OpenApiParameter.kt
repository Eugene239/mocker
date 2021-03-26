package ru.epavlov.mocker.api.open.v20

data class OpenApiParameter(
    val name: String?,
    val `in`: InType,
    val description: String?,
    val required: Boolean? = false,
    val type: OpenApiType? = OpenApiType.string
)