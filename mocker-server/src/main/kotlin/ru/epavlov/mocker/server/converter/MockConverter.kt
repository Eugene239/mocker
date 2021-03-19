package ru.epavlov.mocker.server.converter

import ru.epavlov.mocker.dto.MockDTO
import ru.epavlov.mocker.dto.ParamValuesDTO
import ru.epavlov.mocker.dto.ParamsDTO
import ru.epavlov.mocker.dto.ResponseDTO
import ru.epavlov.mocker.entity.MockEntity
import ru.epavlov.mocker.entity.MockResponse
import ru.epavlov.mocker.entity.ParamEntity
import ru.epavlov.mocker.entity.ParamValue

interface MockConverter {

    fun convertWOParams(entity: MockEntity): MockDTO
    fun convertWOResponse(entity: MockEntity): MockDTO
    fun convertWOResponse(entity: ParamEntity): ParamsDTO

    fun toDTO(entity: MockEntity): MockDTO
    fun toDTO(entity: ParamEntity): ParamsDTO
    fun toDTO(entity: ParamValue): ParamValuesDTO
    fun toDTO(entity: MockResponse?): ResponseDTO?

    fun toEntity(dto: MockDTO): MockEntity
    fun toEntity(dto: ParamsDTO): ParamEntity
    fun toEntity(dto: ParamValuesDTO): ParamValue
    fun toEntity(dto: ResponseDTO?): MockResponse?
}