package ru.epavlov.mocker.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import ru.epavlov.mocker.dto.*

interface MockService {

    fun getMocks(pageable: Pageable): Page<MockDTO>

    fun getMock(id: Long): MockDTO?

    fun getResponse(request: MockRequest): ParamsDTO?

    fun create(mock: MockDTO): MockDTO

    fun update(mock: MockDTO): MockDTO

    fun delete(mockId: Long, param: ParamsDTO): MockDTO

    fun delete(mockId: Long, paramId: Long, value: ParamValuesDTO): MockDTO

    fun delete(mockId: Long, paramId: Long, response: ResponseDTO): MockDTO
}