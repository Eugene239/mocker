package ru.epavlov.mocker.server.converter

import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import ru.epavlov.mocker.api.dto.*
import ru.epavlov.mocker.server.entity.*

import java.time.OffsetDateTime
import java.time.ZoneId

@Component
class MockConverterImpl : MockConverter {


    override fun convertWOParams(entity: MockEntity): MockDTO {
        return fillBaseInfo(entity)
    }

    override fun convertWOResponse(entity: MockEntity): MockDTO {
        val dto = fillBaseInfo(entity)
        dto.params = entity.params.map {
            convertWOResponse(it)
        }
        return dto
    }

    override fun convertWOResponse(entity: ParamEntity): ParamsDTO {
        return ParamsDTO(
                id = entity.id,
                delay = entity.delay,
                code = entity.code.value(),
                values = entity.values.map { toDTO(it) }
        )
    }


    override fun toDTO(entity: MockEntity): MockDTO {
        val dto = fillBaseInfo(entity)
        dto.params = entity.params.map { toDTO(it) }
        return dto
    }


    override fun toDTO(entity: ParamEntity): ParamsDTO {
        return ParamsDTO(
                id = entity.id,
                response = toDTO(entity.response),
                delay = entity.delay,
                code = entity.code.value(),
                values = entity.values.map { toDTO(it) }
        )
    }

    override fun toDTO(entity: ParamValue): ParamValuesDTO {
        return ParamValuesDTO(
                id = entity.id,
                value = entity.value,
                name = entity.name,
                type = entity.type
        )
    }


    override fun toDTO(entity: MockResponse?): ResponseDTO? {
        if (entity == null) return null
        return ResponseDTO(
                id = entity.id,
                body = entity.body
        )
    }


    override fun toEntity(dto: MockDTO): MockEntity {
        return MockEntity(
                path = dto.path.toLowerCase(),
                method = HttpMethod.valueOf(dto.method),
                params = dto.params.map { toEntity(it) }.toMutableList()
        )
    }

    override fun toEntity(dto: ParamsDTO): ParamEntity {
        return ParamEntity(
                delay = dto.delay,
                code = HttpStatus.valueOf(dto.code), // todo handle error
                response = toEntity(dto.response),
                values = dto.values.map { toEntity(it) }.toMutableList()
        )
    }


    override fun toEntity(dto: ParamValuesDTO): ParamValue {
        return ParamValue(
                name = dto.name,
                value = dto.value,
                type = dto.type
        )
    }


    override fun toEntity(dto: ResponseDTO?): MockResponse? {
        if (dto == null) return null
        return MockResponse(
                body = dto.body
        )
    }


    private fun fillBaseInfo(entity: MockEntity): MockDTO {
        return MockDTO(
                id = entity.id,
                path = entity.path.toLowerCase(),
                method = entity.method.name,
                created = OffsetDateTime.ofInstant(entity.created?.toInstant(), ZoneId.systemDefault())
        )
    }

}