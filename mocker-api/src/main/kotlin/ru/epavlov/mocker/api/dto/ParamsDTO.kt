package ru.epavlov.mocker.api.dto


data class ParamsDTO(
    val id: Long? = null,
    val values: List<ParamValuesDTO>,
    val code: Int,
    val delay: Long = 0,
    val response: ResponseDTO? = null
)