package ru.epavlov.mocker.exception

import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus

object ExceptionFabric {
    
    fun mockNotFound( path: String, method: HttpMethod): ApiException{
        return ApiException(
                messageCode = ErrorCode.MOCK_NOT_FOUND,
                httpCode = HttpStatus.NOT_FOUND,
                message = "Mock not found with path=$path and method=$method"
        )
    }
}