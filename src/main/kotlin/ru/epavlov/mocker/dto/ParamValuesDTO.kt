package ru.epavlov.mocker.dto

import ru.epavlov.mocker.entity.ParamType

data class ParamValuesDTO(
    val id: Long? = null,
    val name: String,
    val type: ParamType,
    val value: String
)