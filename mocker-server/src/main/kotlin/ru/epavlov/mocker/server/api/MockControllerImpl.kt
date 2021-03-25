package ru.epavlov.mocker.server.api

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.RestController
import ru.epavlov.mocker.api.MockController
import ru.epavlov.mocker.api.SearchCriteria
import ru.epavlov.mocker.api.dto.MockDTO
import ru.epavlov.mocker.api.dto.PageResponse
import ru.epavlov.mocker.server.service.MockService

@RestController
class MockControllerImpl : MockController {
    companion object {
        val log: Logger = LoggerFactory.getLogger(MockControllerImpl::class.java)
    }

    @Autowired
    lateinit var mockService: MockService

    override fun getMocks(searchCriteria: SearchCriteria): PageResponse<MockDTO> {
        if (log.isDebugEnabled) log.debug("[getMocks] $searchCriteria")
        return toPageResponse(mockService.getMocks(Pageable.unpaged()))
    }

    override fun createMock(mockDTO: MockDTO): MockDTO {
        if (log.isDebugEnabled) log.debug("[createMock] $mockDTO")
        return mockService.create(mockDTO)
    }


    private fun toPageResponse(page: Page<MockDTO>): PageResponse<MockDTO> {
        return PageResponse(
            page.content,
            page.size,
            page.number,
            page.totalPages,
            page.totalElements
        )
    }
}