package ru.epavlov.mocker.service

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import ru.epavlov.mocker.BaseTest
import ru.epavlov.mocker.MockerApplication
import ru.epavlov.mocker.dto.MockDTO
import ru.epavlov.mocker.dto.ParamValuesDTO
import ru.epavlov.mocker.dto.ParamsDTO
import ru.epavlov.mocker.dto.ResponseDTO
import ru.epavlov.mocker.entity.ParamType
import ru.epavlov.mocker.repository.MockRepository
import java.util.*


@SpringBootTest(classes = [MockerApplication::class])
@ActiveProfiles(profiles = ["test"])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class MockServiceTest : BaseTest() {
    companion object {
        val log = LoggerFactory.getLogger(MockRepository::class.java)

    }


    @Autowired
    lateinit var service: MockService


    @Test
    @DisplayName("Check create in Service")
    fun checkCreate() {
        var r1 = service.create(createMock())
        log.info("r1 created: $r1")
        var r2 = service.create(createMock())
        log.info("r2 created: $r2")
        assert(2L == service.getMocks(Pageable.unpaged()).totalElements)
        checkResponseIsNull(r1, true)
        checkResponseIsNull(r2, true)

        // checking response created
        r1 = service.getMock(r1.id!!)!!
        r2 = service.getMock(r2.id!!)!!
        checkResponseIsNull(r1, true)
        checkResponseIsNull(r2, true)
    }

    @Test
    @DisplayName("Check find by queryParams")
    fun checkFindByQueryParams() {
        val contactId = "123123-123123-3123"
        val sessionId = "2134-1424-142"
        val response  = """{"test": "value"}"""
        val path = "/check/method"
        val method = HttpMethod.GET
        val code = HttpStatus.OK


        val mock1 = MockDTO(
                path = path,
                params = listOf(ParamsDTO(
                        code = code,
                        delay = 0,
                        response = ResponseDTO(
                                body = "nope"
                        ),
                        values = listOf(ParamValuesDTO(
                                type = ParamType.QUERY_PARAM,
                                name = "sessionId",
                                value = sessionId
                        ),
                                ParamValuesDTO(
                                        type = ParamType.QUERY_PARAM,
                                        name = "contactId",
                                        value = contactId
                                ))
                )),
                method = HttpMethod.POST

        )
        service.create(mock1)

        val mock2 = MockDTO(
                path = path,
                params = listOf(ParamsDTO(
                        code = code,
                        delay = 0,
                        response = ResponseDTO(
                                body = "nope"
                        ),
                        values = listOf(ParamValuesDTO(
                                type = ParamType.QUERY_PARAM,
                                name = "sessionId",
                                value = sessionId
                        ),
                                ParamValuesDTO(
                                        type = ParamType.QUERY_PARAM,
                                        name = "contactId",
                                        value = "wrong"
                                ))
                )),
                method = method

        )
        service.create(mock2)

        val mock3 = MockDTO(
                path = path,
                params = listOf(ParamsDTO(
                        code = code,
                        delay = 0,
                        response = ResponseDTO(
                                body = "nope"
                        ),
                        values = listOf()
                )),
                method = method

        )
        service.create(mock3)

        val mock4 = MockDTO(
                path = path,
                params = listOf(ParamsDTO(
                        code = code,
                        delay = 0,
                        response = ResponseDTO(
                                body = response
                        ),
                        values = listOf(ParamValuesDTO(
                                type = ParamType.HEADER,
                                name = "sessionId",
                                value = sessionId
                        ),
                                ParamValuesDTO(
                                        type = ParamType.QUERY_PARAM,
                                        name = "contactId",
                                        value = contactId
                                ))
                )),
                method = method

        )
       service.create(mock4)

        val mock = MockDTO(
                path = path,
                params = listOf(ParamsDTO(
                    code = code,
                    delay = 0,
                    response = ResponseDTO(
                            body = response
                    ),
                        values = listOf(ParamValuesDTO(
                               type = ParamType.QUERY_PARAM,
                               name = "sessionId",
                               value = sessionId
                        ),
                        ParamValuesDTO(
                              type = ParamType.QUERY_PARAM,
                                name = "contactId",
                                value = contactId
                        ))
                )),
                method = method

        )
        var r1 = service.create(mock)
        val found =  service.getResponse(path, method, mapOf("sessionId" to listOf(sessionId), "contactId" to listOf(contactId)) , emptyMap())
        log.info("response $found")
        assert(found!=null)
        assert(found?.response?.body == response)
        assert(found?.code == code)
    }

    @Test
    @DisplayName("not found 1")
    fun notFoundParamTest() {
        val r1 = service.create(createMock())
        log.info("r1 created: $r1")
        val response = service.getResponse(r1.path, r1.method, emptyMap(), emptyMap())
        assert(!r1.params.isNullOrEmpty())
        assert(response == null)
    }

    @Test
    @DisplayName("not found 2")
    fun notFoundParamTest2() {
        val contactId = "123123-123123-3123"
        val sessionId = "2134-1424-142"
        val response  = """{"test": "value"}"""
        val path = "/check/method"
        val method = HttpMethod.GET
        val code = HttpStatus.OK

        val mock = MockDTO(
                path = path,
                params = listOf(ParamsDTO(
                        code = code,
                        delay = 0,
                        response = ResponseDTO(
                                body = response
                        ),
                        values = listOf(ParamValuesDTO(
                                type = ParamType.QUERY_PARAM,
                                name = "sessionId",
                                value = sessionId
                        ),
                                ParamValuesDTO(
                                        type = ParamType.QUERY_PARAM,
                                        name = "contactId",
                                        value = contactId
                                ))
                )),
                method = method

        )
        var r1 = service.create(mock)
        var found =  service.getResponse(path, method, mapOf("sessionId" to listOf(UUID.randomUUID().toString()), "contactId" to listOf(contactId)) , emptyMap())
        assert(found == null)
        found =  service.getResponse(path, method, mapOf("sessionId" to listOf(sessionId), "contactId" to listOf(UUID.randomUUID().toString())) , emptyMap())
        assert(found == null)
    }


    fun checkResponseIsNull(dto: MockDTO, isNull: Boolean) {
        dto.params.forEach {
            if (isNull)
                assert(it.response?.body == null)
            else
                assert(it.response?.body != null)
        }
    }



}