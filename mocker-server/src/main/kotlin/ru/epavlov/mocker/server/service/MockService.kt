package ru.epavlov.mocker.server.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import ru.epavlov.mocker.api.dto.MockDTO
import ru.epavlov.mocker.api.dto.ParamsDTO
import ru.epavlov.mocker.server.api.dto.MockRequest

interface MockService {

    fun getMocks(pageable: Pageable): Page<MockDTO>

    fun getMock(id: Long): MockDTO?

    fun getResponse(request: MockRequest): ParamsDTO?

    fun create(mock: MockDTO): MockDTO

    fun update(mock: MockDTO): MockDTO

    fun delete(mockId: Long, param: ParamsDTO): MockDTO

    fun delete(mockId: Long, paramId: Long, paramValueId: Long): MockDTO

    fun deleteResponse(mockId: Long, paramId: Long): MockDTO
}