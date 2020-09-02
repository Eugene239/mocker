package ru.epavlov.mocker.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpMethod
import ru.epavlov.mocker.dto.MockDTO
import ru.epavlov.mocker.dto.ParamValuesDTO
import ru.epavlov.mocker.dto.ParamsDTO
import ru.epavlov.mocker.dto.ResponseDTO

interface MockService {

    fun getMocks(pageable: Pageable): Page<MockDTO>

    fun getMock(id: Long): MockDTO?

    fun getResponse(path: String, method: HttpMethod, queryParams: Map<String, List<String>>, headers: Map<String, List<String>>): ParamsDTO?

    fun create(mock: MockDTO): MockDTO

    fun update(mock: MockDTO): MockDTO

    fun delete(param: ParamsDTO): MockDTO

    fun delete(value: ParamValuesDTO): MockDTO

    fun delete(response: ResponseDTO): MockDTO
}