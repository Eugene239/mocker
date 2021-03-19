package ru.epavlov.mocker.api

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import ru.epavlov.mocker.api.dto.MockDTO
import ru.epavlov.mocker.api.dto.PageResponse

@Api("Api for Mocker")
@RequestMapping("/mocks")
interface MockController {

    @ApiOperation(value = "Get all Mocks with paging")
    @GetMapping()
    fun getMocks(
        @ApiParam(value = "pageSize", example = "20", required = false)
        @RequestParam("pageSize") pageSize: Int?,
        @ApiParam(value = "pageNumber", example = "1", required = false)
        @RequestParam("pageNumber") pageNumber: Int?
    ): PageResponse<MockDTO>


}