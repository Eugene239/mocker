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

    fun paramValueNotFound(): ApiException{
        return ApiException(
                messageCode = ErrorCode.PARAM_VALUE_NOT_FOUND,
                httpCode = HttpStatus.NOT_FOUND
        )
    }

    fun wrongMockId(): ApiException{
        return ApiException(
                messageCode = ErrorCode.WRONG_MOCK_ID,
                httpCode = HttpStatus.BAD_REQUEST
        )
    }

    fun wrongParamId(): ApiException{
        return ApiException(
                messageCode = ErrorCode.WRONG_PARAM_ID,
                httpCode = HttpStatus.BAD_REQUEST
        )
    }

    fun IdRequired(): ApiException{
        return ApiException(
                messageCode = ErrorCode.ID_REQUIRED,
                httpCode = HttpStatus.BAD_REQUEST
        )
    }
}