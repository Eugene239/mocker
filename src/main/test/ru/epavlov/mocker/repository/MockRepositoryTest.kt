package ru.epavlov.mocker.repository

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import ru.epavlov.mocker.MockerApplication
import ru.epavlov.mocker.controller.MockController
import ru.epavlov.mocker.entity.MockEntity
import ru.epavlov.mocker.entity.MockResponse
import ru.epavlov.mocker.entity.Param

@SpringBootTest(classes = [MockerApplication::class])
@ActiveProfiles(profiles = ["test"])
//@AutoConfigureWireMock(port = 0)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class MockRepositoryTest {
    companion object {
        val log = LoggerFactory.getLogger(MockRepository::class.java)
    }


    @Autowired
    lateinit var repository: MockRepository

    @AfterEach
    protected fun cleanup() {
        repository.deleteAll()
    }

    @Test
    @DisplayName("Check create in DB")
    fun checkCreate() {
        val result = repository.save(MockEntity(
                "/test/path",
                HttpMethod.GET
        ))
        log.info("create result: $result")
        assert(result.id != null)
        assert(result.created != null)
        assert(result.updated != null)
        log.info("${repository.findAll()}")
    }

    @Test
    @DisplayName("Check unique constraint")
    fun checkUnique() {
        val mock1 = MockEntity(
                "/test/path",
                HttpMethod.GET
        )
        val mock2 = MockEntity(
                "/test/path2",
                HttpMethod.POST
        )
        val mock3 = MockEntity(
                mock1.path,
                HttpMethod.DELETE
        )
        repository.save(mock1)
        repository.save(mock2)
        repository.save(mock3)
        assert(repository.count() == 3L)

        //check throws cause of unique constraint
        assertThrows<DataIntegrityViolationException> {
            repository.save(MockEntity(
                    mock1.path,
                    mock1.method
            ))
        }
        log.info("result: ${repository.findAll()}")
    }

    @Test
    @DisplayName("Create mock full data")
    fun createFull() {
        val mock = MockEntity(
                "/test/path",
                HttpMethod.POST
        )

        val param = Param(
                //type = ParamType.QUERY_PARAM,
                //name = "sessionId",
                //value = "'3123-3123123-3123'"

        ).apply { this.response = MockResponse(
                code = HttpStatus.NOT_FOUND,
                delay = 1000,
                body = "hello"
        ) }

        mock.params.add(param)
        var result = repository.save(mock)
        MockController.log.info("result $result")
        MockController.log.info("params: ${result.params}")
//        result.params!!.forEach {
//            assert(it.mockId!=null)
//        }
        result.params.add(Param().apply { this.response= MockResponse(
                code = HttpStatus.CREATED,
                delay = 0,
                body = "retard"
        ) })
        result = repository.save(result)
        MockController.log.info("result $result")
        MockController.log.info("params: ${result.params}")
    }


}