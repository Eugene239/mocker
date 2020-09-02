package ru.epavlov.mocker.dto

import org.springframework.http.HttpStatus

data class ParamsDTO(
        val id: Long? = null,
        val values: List<ParamValuesDTO>,
        val code: HttpStatus = HttpStatus.OK,
        val delay: Long = 0,
        val response: ResponseDTO? = null
)