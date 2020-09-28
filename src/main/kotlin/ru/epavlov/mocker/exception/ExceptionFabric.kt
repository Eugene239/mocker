package ru.epavlov.mocker.exception

import org.springframework.http.HttpStatus

object ExceptionFabric {

    fun mockNotFound(): ApiException{
        return ApiException(
                messageCode = ErrorCode.MOCK_NOT_FOUND,
                httpCode = HttpStatus.NOT_FOUND
        )
    }

    fun paramNotFound(): ApiException{
        return ApiException(
                messageCode = ErrorCode.PARAM_NOT_FOUND,
                httpCode = HttpStatus.NOT_FOUND
        )
    }

    fun IdRequired(): ApiException{
        return ApiException(
                messageCode = ErrorCode.ID_REQUIRED,
                httpCode = HttpStatus.BAD_REQUEST
        )
    }
}