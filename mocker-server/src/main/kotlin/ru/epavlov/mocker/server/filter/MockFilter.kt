package ru.epavlov.mocker.server.filter

import org.apache.tomcat.util.json.JSONParser
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import ru.epavlov.mocker.server.api.dto.MockRequest
import ru.epavlov.mocker.server.config.MockConfig
import ru.epavlov.mocker.server.controller.ResponseController
import ru.epavlov.mocker.server.exception.ExceptionFabric
import ru.epavlov.mocker.server.service.MockService
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.math.min

@Component
class MockFilter : OncePerRequestFilter() {

    companion object {
        val log: Logger = LoggerFactory.getLogger(MockFilter::class.java)
    }

    @Autowired
    lateinit var service: MockService

    @Autowired
    lateinit var config: MockConfig


    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        if (log.isDebugEnabled) log.debug("[FILTER] path=${request.requestURI}  method=${request.method} ")
        val start = System.currentTimeMillis()
        val mock = service.getResponse(MockRequest.fromRequest(request))

        mock?.let {
            if (log.isDebugEnabled) ResponseController.log.debug("[FILTER] mockDTO: $mock ")

            val delay = min(mock.delay, config.maxDelay)
            //sleep some time
            if (System.currentTimeMillis() - start < delay) {
                Thread.sleep(delay - System.currentTimeMillis() + start)
            }

            response.status = mock.code

            mock.response?.body?.let {
                val writer = response.writer
                writer.write(it)
                writer.flush()
            }
            if (log.isDebugEnabled) ResponseController.log.debug("[FILTER] body: ${mock.response?.body}")
            return
        }
        if (log.isDebugEnabled) log.debug("[FILTER] No Mock")
        chain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val uri = request.requestURI
        return uri.contains("springfox-swagger-ui")
                || uri.contains("swagger-resources")
                || uri == "/v2/api-docs"
                || uri == "/"
                || uri == "/csrf"
                || uri == "/mocks"
                || uri == "/mocks/create"
                || uri == "/swagger-ui.html"
    }
}