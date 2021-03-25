package ru.epavlov.mocker.server.controller

import org.apache.tomcat.util.json.JSONParser
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.annotation.Order
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import ru.epavlov.mocker.server.api.dto.MockRequest
import ru.epavlov.mocker.server.config.MockConfig
import ru.epavlov.mocker.server.exception.ExceptionFabric
import ru.epavlov.mocker.server.service.MockService

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.math.min

@CrossOrigin(
        origins = ["*"],
        allowedHeaders = ["*"],
        methods = [RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST, RequestMethod.DELETE]
)
@Order(2147483647)
//@RestController todo move to filter
class ResponseController {
    companion object {
        val log: Logger = LoggerFactory.getLogger(ResponseController::class.java)
        const val ROOT = "/"
    }

    @Autowired
    lateinit var service: MockService

    @Autowired
    lateinit var config: MockConfig


    @RequestMapping("/**")
    @Order(1)
    fun allMapping(request: HttpServletRequest, response: HttpServletResponse
    ): ResponseEntity<Any> {
        val start = System.currentTimeMillis()
        if (log.isDebugEnabled) log.debug("[REQUEST] path=${request.requestURI}  method=${request.method} ")

        val mockRequest = MockRequest.fromRequest(request)

        val mock = service.getResponse(mockRequest) ?: throw ExceptionFabric.mockNotFound()
        if (log.isDebugEnabled) log.debug("[RESPONSE] mock: $mock ")

        val responseBody = if (mock.response?.body != null) {
            JSONParser(mock.response!!.body).parse()
        } else {
            null
        }

        val delay = min(mock.delay, config.maxDelay)
        //sleep some time
        if (System.currentTimeMillis() - start < delay) {
            Thread.sleep(delay - System.currentTimeMillis() + start)
        }

        return ResponseEntity.status(mock.code)
                .body(responseBody)
    }
}