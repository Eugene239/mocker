package ru.epavlov.mocker.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorDTO(
        val code: String? = null,
        val message: String? = null
)