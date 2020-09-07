package ru.epavlov.mocker.controller

import org.apache.tomcat.util.json.JSONParser
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.annotation.Order
import org.springframework.core.env.Environment
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import ru.epavlov.mocker.dto.MockRequest
import ru.epavlov.mocker.exception.ExceptionFabric
import ru.epavlov.mocker.service.MockService
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 *       !WARNING!
 *  Dont override '/' path
 *
 */

@CrossOrigin(
        origins = ["*"],
        allowedHeaders = ["*"],
        methods = [RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST, RequestMethod.DELETE]
)
@Order(1)
@RestController
class MockController {
    //todo add swagger?

    companion object {
        val log: Logger = LoggerFactory.getLogger(MockController::class.java)
        const val ROOT = "/"
    }

    // todo move to config
    @Value("\${mocker.uuid}")
    lateinit var uuid: String

    @Value("\${mocker.path.regex}")
    lateinit var regex: String;

    @Value("\${mocker.methods}")
    lateinit var methods: List<String>
    //

    @Autowired
    lateinit var env: Environment

    @Autowired
    lateinit var service: MockService



    //    @GetMapping("\${mocker.uuid}")
//    fun getMapping(pageable: Pageable): Page<MockEntity> {
//        return repository.findAll(pageable).map { it.body = null; it } // todo sql wo body
//    }
//
//    @GetMapping("uuid")
//    fun getUUID(): Any {
//        return mapOf("uuid" to uuid);
//    }
//
//
//    @PostMapping("\${mocker.uuid}")
//    fun create(
//            @RequestParam("path", required = true) _path: String,
//            @RequestParam("method", required = true) method: HttpMethod,
//            @RequestParam("code", required = false, defaultValue = "200") code: Int? = 200,
//            @RequestBody(required = false) json: String?): ResponseEntity<Any> {
//
//        if (log.isDebugEnabled) log.debug("[CREATE] path: $_path, method: $method, code: $code, body: $json")
//        val path = _path.trim().toLowerCase()
//
//        if (!regex.toRegex().matches(path)) {
//            return ResponseEntity.badRequest().body(mapOf("code" to "BAD_PATH", "message" to "Cant create mock method, PATH invalid"));
//        }
//        if (!methods.contains(method.name)) {
//            return ResponseEntity.badRequest().body(mapOf("code" to "BAD_METHOD", "message" to "Cant create mock method, METHOD invalid"));
//        }
//        if (HttpStatus.resolve(code!!) == null) {
//            return ResponseEntity.badRequest().body(mapOf("code" to "BAD_CODE", "message" to "Cant create mock method, CODE invalid"));
//        }
//
//
//        try {
//            //validate json
//            json?.let {
//                mapper.readTree(json)
//            }
//
//            repository.save(MockEntity(
//                    path = path,
//                    code = code,
//                    method = method.name.toUpperCase(),
//                    body = json
//            ))
//        } catch (e: Exception) {
//            log.error("cant save $path,$method $code $json", e)
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
//        }
//        return ResponseEntity.ok().build();
//    }
//
    // @RequestMapping("/{prefix}/{main}/**")
    @RequestMapping("/**")
    @Order(1)
    fun allMapping(
            request: HttpServletRequest,
            response: HttpServletResponse
    ): ResponseEntity<Any> {
        val start = System.currentTimeMillis()
        if (log.isDebugEnabled) log.debug("[REQUEST] path=${request.requestURI}  method=${request.method} ")

        val mockRequest = MockRequest.fromRequest(request)

        val mock = service.getResponse(mockRequest)
                ?: throw ExceptionFabric.mockNotFound(mockRequest.path, mockRequest.method)
        if (log.isDebugEnabled) log.debug("[RESPONSE] mock: $mock ")

        val responseBody = if (mock.response?.body != null) {
            JSONParser(mock.response.body).parse()
        } else {
            null
        }

        //sleep some time
        if (System.currentTimeMillis() - start < mock.delay) {
            Thread.sleep(mock.delay - System.currentTimeMillis() + start)
        }

        return ResponseEntity.status(mock.code)
                .body(responseBody)
    }


}
