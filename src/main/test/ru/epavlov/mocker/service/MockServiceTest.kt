package ru.epavlov.mocker.service

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Pageable
import org.springframework.test.context.ActiveProfiles
import ru.epavlov.mocker.BaseTest
import ru.epavlov.mocker.MockerApplication
import ru.epavlov.mocker.dto.MockDTO
import ru.epavlov.mocker.repository.MockRepository


@SpringBootTest(classes = [MockerApplication::class])
@ActiveProfiles(profiles = ["test"])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class MockServiceTest : BaseTest(){
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
//        var mock1 =
//        var r1 = service.create(createMock())
//        log.info("r1 created: $r1")
//        var r2 = service.create(createMock())
//        log.info("r2 created: $r2")
//        assert(2L == service.getMocks(Pageable.unpaged()).totalElements)
//        checkResponseIsNull(r1, true)
//        checkResponseIsNull(r2, true)
//
//        // checking response created
//        r1 = service.getMock(r1.id!!)!!
//        r2 = service.getMock(r2.id!!)!!
//        checkResponseIsNull(r1, true)
//        checkResponseIsNull(r2, true)
    }

    @Test
    @DisplayName("not found")
    fun notFoundParamTest() {
        val r1 = service.create(createMock())
        log.info("r1 created: $r1")
        val response =  service.getResponse(r1.path, r1.method, emptyMap(), emptyMap() )
        assert(!r1.params.isNullOrEmpty())
        assert(response == null)
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