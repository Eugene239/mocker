package ru.epavlov.mocker.api

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiParam
import org.springframework.web.bind.annotation.RequestParam

@ApiModel(description = "Search params for mocks")
data class SearchCriteria(
    @ApiParam(value = "path", example = "/test/src", required = false)
    @RequestParam("path")
    val path: String?,
    @ApiParam(value = "httpMethod", example = "POST", required = false)
    @RequestParam("httpMethod")
    val httpMethod: String?,
    @ApiParam(value = "pageSize", example = "20", required = false)
    @RequestParam("pageSize")
    val pageSize: Int? = 20,
    @ApiParam(value = "pageNumber", example = "1", required = false)
    @RequestParam("pageNumber")
    val pageNumber: Int? = 0
)