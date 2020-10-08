package ru.epavlov.mocker.dto

import org.springframework.http.HttpMethod
import javax.servlet.http.HttpServletRequest

data class MockRequest(
        val path: String,
        val method: HttpMethod,
        val queryParams: Map<String, List<String>>,
        val headers: Map<String, List<String>>
) {
    companion object {
        fun fromRequest(request: HttpServletRequest): MockRequest {
            val path = request.requestURI.toLowerCase()
            val method = HttpMethod.valueOf(request.method)

            val queryParams = request.parameterMap.map {
                it.key to it.value.map {value-> value.toLowerCase() }.toList()
            }.toMap()

            val headerNames = request.headerNames.asSequence()
            val headers = headerNames.map { header ->
                header to request.getHeaders(header).toList()
            }.toMap()

            return MockRequest(
                    path,
                    method,
                    queryParams,
                    headers
            )
        }
    }
}