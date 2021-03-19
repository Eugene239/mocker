package ru.epavlov.mocker.api.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(description = "Generic for paging response")
data class PageResponse<T>(
    @ApiModelProperty(value = "Page contents")
    val items: List<T>,
    @ApiModelProperty(value = "Current pageSize")
    val pageSize: Int,
    @ApiModelProperty(value = "Current pageNumber")
    val pageNumber: Int,
    @ApiModelProperty(value = "Total amount of elements")
    val total: Int
)