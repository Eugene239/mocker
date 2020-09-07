package ru.epavlov.mocker.exception

import org.springframework.http.HttpStatus
import java.lang.RuntimeException

data class ApiException(
        val messageCode: ErrorCode = ErrorCode.UNKNOWN_ERROR,
        val httpCode: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
        override val message: String? = null,
        val args: List<Any> = emptyList()
) : RuntimeException()