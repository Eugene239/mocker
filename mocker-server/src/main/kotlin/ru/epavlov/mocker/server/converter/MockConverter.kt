package ru.epavlov.mocker.server.converter

import ru.epavlov.mocker.api.dto.*
import ru.epavlov.mocker.server.entity.*


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