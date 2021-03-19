package ru.epavlov.mocker.api

import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.RequestMapping
import ru.epavlov.mocker.api.dto.MockDTO
import ru.epavlov.mocker.api.dto.PageResponse

@Api("Api for Mocker")
@RequestMapping("/mocks")
interface MockController {

    fun getMocks(): PageResponse<MockDTO>


}