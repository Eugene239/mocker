package ru.epavlov.mocker

import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import ru.epavlov.mocker.dto.MockDTO
import ru.epavlov.mocker.dto.ParamValuesDTO
import ru.epavlov.mocker.dto.ParamsDTO
import ru.epavlov.mocker.dto.ResponseDTO
import ru.epavlov.mocker.entity.ParamType
import java.util.*
import kotlin.collections.HashMap
import kotlin.random.Random

open class BaseTest {
    val methods = arrayOf(HttpMethod.POST, HttpMethod.DELETE, HttpMethod.GET, HttpMethod.PUT)

    fun createMock(): MockDTO {
        return MockDTO(
                method = methods[Random.nextInt(methods.size)],
                path = UUID.randomUUID().toString(),
                params = IntRange(0, Random.nextInt(10)+1).map { createParam() }
        )
    }

    fun createParam(): ParamsDTO {
        return ParamsDTO(
                values = IntRange(0, Random.nextInt(10)).map { createValue() },
                response = createResponse(),
                code = HttpStatus.values()[Random.nextInt(HttpStatus.values().size)],
                delay = Random.nextInt(10000).toLong()

        )
    }

    fun createValue(): ParamValuesDTO {
        return ParamValuesDTO(
                name = UUID.randomUUID().toString(),
                value = UUID.randomUUID().toString(),
                type = ParamType.QUERY_PARAM
        )
    }

    fun createResponse(): ResponseDTO {
        return ResponseDTO(
                body = UUID.randomUUID().toString()
        )
    }

    fun toMaps(params: ParamsDTO): Pair<Map<String, List<String>> /*headers*/, Map<String, List<String>>/* qParams*/> {
        val headerMap = HashMap<String, List<String>>()
        val qMap  = HashMap<String,List<String>>()
        params.values.forEach {pValue ->
            if (ParamType.QUERY_PARAM ==  pValue.type){
                qMap.plus(pValue.name to pValue.value)
            }
            if (ParamType.HEADER == pValue.type){
                headerMap.plus(pValue.name to pValue.value)
            }
        }
        return headerMap to qMap
    }
}