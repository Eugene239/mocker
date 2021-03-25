package ru.epavlov.mocker.api

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.web.bind.annotation.*
import ru.epavlov.mocker.api.dto.MockDTO
import ru.epavlov.mocker.api.dto.PageResponse

@Api("Api for Mocker")
@RequestMapping("/mocks")
interface MockController {

    @ApiOperation(value = "Get all Mocks with paging")
    @PostMapping
    fun getMocks(
        @ApiParam(value = "Search request", required = false)
        @RequestBody
        searchCriteria: SearchCriteria): PageResponse<MockDTO>


}