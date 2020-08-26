package ru.epavlov.mocker.dto

import ru.epavlov.mocker.entity.ParamType

data class ParamValuesDTO(
    val id: Long? = null,
    val name: String? = null,
    val type: ParamType? = null,
    val value: String? = null
)