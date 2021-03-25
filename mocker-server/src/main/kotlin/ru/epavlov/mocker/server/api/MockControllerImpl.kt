package ru.epavlov.mocker.server.api

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RestController
import ru.epavlov.mocker.api.MockController
import ru.epavlov.mocker.api.SearchCriteria
import ru.epavlov.mocker.api.dto.MockDTO
import ru.epavlov.mocker.api.dto.PageResponse

@RestController
class MockControllerImpl : MockController {
    companion object{
        val log: Logger = LoggerFactory.getLogger(MockControllerImpl::class.java)
    }


    override fun getMocks(searchCriteria: SearchCriteria): PageResponse<MockDTO> {
        if (log.isDebugEnabled) log.debug("[getMocks] $searchCriteria")
        return PageResponse(emptyList(), searchCriteria.pageSize!!, searchCriteria.pageNumber!!, 0)
    }

}