package ru.epavlov.mocker.dto

import org.springframework.http.HttpStatus

data class ParamsDTO(
        val id: Long? = null,
        val values: List<ParamValuesDTO>? = null,
        val code: HttpStatus? = null,
        val delay: Long? = null,
        val response: ResponseDTO? = null
)