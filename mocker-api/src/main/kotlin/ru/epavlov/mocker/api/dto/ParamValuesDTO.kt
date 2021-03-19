package ru.epavlov.mocker.api.dto


data class ParamValuesDTO(
    val id: Long? = null,
    val name: String,
    val type: ParamType,
    val value: String
)