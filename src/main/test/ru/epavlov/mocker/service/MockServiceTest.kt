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
import ru.epavlov.mocker.MockerApplication
import ru.epavlov.mocker.dto.MockDTO
import ru.epavlov.mocker.dto.ParamValuesDTO
import ru.epavlov.mocker.dto.ParamsDTO
import ru.epavlov.mocker.dto.ResponseDTO
import ru.epavlov.mocker.entity.ParamType
import ru.epavlov.mocker.repository.MockRepository
import java.util.*
import kotlin.random.Random


@SpringBootTest(classes = [MockerApplication::class])
@ActiveProfiles(profiles = ["test"])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class MockServiceTest {
    companion object {
        val log = LoggerFactory.getLogger(MockRepository::class.java)

    }

    val methods = arrayOf(HttpMethod.POST, HttpMethod.DELETE, HttpMethod.GET, HttpMethod.PUT)

    @Autowired
    lateinit var service: MockService


    @Test
    @DisplayName("Check create in Service")
    fun checkCreate() {
        val r1 = service.create(createMock())
        log.info("r1 created: $r1")
        val r2 = service.create(createMock())
        log.info("r2 created: $r2")
        assert(2L == service.getMocks(Pageable.unpaged()).totalElements)
    }


    fun createMock(): MockDTO {


        return MockDTO(
                method = methods[Random.nextInt(methods.size)],
                path = UUID.randomUUID().toString(),
                params = IntRange(0, Random.nextInt(10)).map { createParam() }
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
}