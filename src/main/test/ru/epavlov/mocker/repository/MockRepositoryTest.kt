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
import ru.epavlov.mocker.entity.*

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
                path = "/test/path",
                method = HttpMethod.GET
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
                path = "/test/path",
                method = HttpMethod.GET
        )
        val mock2 = MockEntity(
                path = "/test/path2",
                method = HttpMethod.POST
        )
        val mock3 = MockEntity(
                path = mock1.path,
                method = HttpMethod.DELETE
        )
        repository.save(mock1)
        repository.save(mock2)
        repository.save(mock3)
        assert(repository.count() == 3L)

        //check throws cause of unique constraint
        assertThrows<DataIntegrityViolationException> {
            repository.save(MockEntity(
                    path = mock1.path,
                    method = mock1.method
            ))
        }
        log.info("result: ${repository.findAll()}")
    }

    @Test
    @DisplayName("Create mock full data")
    fun createFull() {
        val path = "/test/path"
        val method = HttpMethod.POST
        val mock = MockEntity(
                path = path,
                method = method
        )
        val response = MockResponse(
                code = HttpStatus.INTERNAL_SERVER_ERROR,
                delay = 231213,
                body = """{"hello":"test"}"""
        )

        val paramV1= ParamValue(
                type = ParamType.HEADER,
                name = "Content-Type",
                value = "text/plain"
        )
        val paramV2 = ParamValue(
                type = ParamType.QUERY_PARAM,
                name = "sessionId",
                value= "'2313-321312-311231'"
        )

        val param = Param(
                response = response,
                values = mutableListOf(paramV1,paramV2)
        )

        mock.params = mutableListOf(param)

        var result = repository.save(mock)

        assert(result.id!=null)
        assert(!result.params.isNullOrEmpty())
        assert(path == result.path)
        assert(method == result.method)

        result.params.forEach { p->
            assert(p.id!=null)
            assert(p.response!=null)
            assert(p.created!=null)
            assert(p.updated!=null)
            assert(!p.values.isNullOrEmpty())

            p.values.forEach { v->
                assert(v.id!=null)
                assert(v.name!=null)
                assert(v.value!=null)
                assert(v.type!=null)
                assert(v.created!=null)
                assert(v.updated!=null)
            }
        }


    }


}