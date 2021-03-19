package ru.epavlov.mocker.server.api

import org.springframework.web.bind.annotation.RestController
import ru.epavlov.mocker.api.MockController
import ru.epavlov.mocker.api.dto.MockDTO
import ru.epavlov.mocker.api.dto.PageResponse

@RestController
class MockControllerImpl : MockController {
    override fun getMocks(pageSize: Int?, pageNumber: Int?): PageResponse<MockDTO> {
        return PageResponse(emptyList(), pageSize ?: 0, pageNumber ?: 0, 0);
    }
}