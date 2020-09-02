package ru.epavlov.mocker.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.annotation.Order
import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

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
@Order(2)
@RestController
class MockController {
    //todo add swagger?

    companion object {
        val log: Logger = LoggerFactory.getLogger(MockController::class.java)
        const val ROOT = "/"
    }

    @Value("\${mocker.uuid}")
    lateinit var uuid: String

    @Value("\${mocker.path.regex}")
    lateinit var regex: String;

    @Value("\${mocker.methods}")
    lateinit var methods: List<String>


    @Autowired
    lateinit var env: Environment


    @Autowired
    lateinit var mapper: ObjectMapper



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
//    @RequestMapping("/{prefix}/{main}/**")
//    fun allMapping(
//        @PathVariable("prefix") prefix: String,
//        @PathVariable("main") main: String,
//        request: HttpServletRequest,
//        response: HttpServletResponse
//    ): ResponseEntity<Any> {
//        if (log.isDebugEnabled) log.debug("[REQUEST] path=${request.requestURI}  method=${request.method} ")
//        val mock = repository.findById(MockEntityId(request.requestURI.toLowerCase(), request.method.toUpperCase())).orElse(null)
//        if (log.isDebugEnabled) log.debug("[RESPONSE] mock: $mock ")
//
//
//        return if (mock == null) {
//            ResponseEntity.notFound().build();
//        } else {
//            var responseBody: Any? = null;
//            if (mock.body != null) {
//                responseBody = JSONParser(mock.body).parse();
//            }
//
//            ResponseEntity.status(mock.code ?: 200)
//                    .body(responseBody)
//        }
//
//    }


}
