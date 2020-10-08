package ru.epavlov.mocker.dto

import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import ru.epavlov.mocker.entity.ParamType
import ru.epavlov.mocker.repository.MockRepository


class TestDtoExists {

    companion object {
        val log = LoggerFactory.getLogger(MockRepository::class.java)
    }

    @Test
    fun checkParamExists() {
        val param = ParamValuesDTO(
                type = ParamType.HEADER,
                name = "m",
                value = "k"
        )

        val duplicate = ParamValuesDTO(
                type = ParamType.HEADER,
                name = "m",
                value = "k"
        )

        val param2 = ParamValuesDTO(
                type = ParamType.QUERY_PARAM,
                name = "m",
                value = "k"
        )

        val params = listOf(duplicate, param2)
        val params2 = listOf(param2)

        assert(params.contains(param))
        assert(!params2.contains(param))
    }

}