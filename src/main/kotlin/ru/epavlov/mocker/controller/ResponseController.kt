package ru.epavlov.mocker.controller

import org.apache.tomcat.util.json.JSONParser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.annotation.Order
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import ru.epavlov.mocker.config.MockConfig
import ru.epavlov.mocker.dto.MockRequest
import ru.epavlov.mocker.exception.ExceptionFabric
import ru.epavlov.mocker.service.MockService
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.math.min

@CrossOrigin(
        origins = ["*"],
        allowedHeaders = ["*"],
        methods = [RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST, RequestMethod.DELETE]
)
@Order(100)
@RestController
class ResponseController {

    @Autowired
    lateinit var service: MockService

    @Autowired
    lateinit var config: MockConfig


    @RequestMapping("/**")
    @Order(1)
    fun allMapping(request: HttpServletRequest, response: HttpServletResponse
    ): ResponseEntity<Any> {
        val start = System.currentTimeMillis()
        if (MockController.log.isDebugEnabled) MockController.log.debug("[REQUEST] path=${request.requestURI}  method=${request.method} ")

        val mockRequest = MockRequest.fromRequest(request)

        val mock = service.getResponse(mockRequest)
                ?: throw ExceptionFabric.mockNotFound(mockRequest.path, mockRequest.method)
        if (MockController.log.isDebugEnabled) MockController.log.debug("[RESPONSE] mock: $mock ")

        val responseBody = if (mock.response?.body != null) {
            JSONParser(mock.response.body).parse()
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