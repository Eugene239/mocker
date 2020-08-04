package ru.epavlov.mocker.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.tomcat.util.json.JSONParser
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.annotation.Order
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import ru.epavlov.mocker.repository.MockEntity
import ru.epavlov.mocker.repository.MockEntityId
import ru.epavlov.mocker.repository.MockRepository
import java.io.InputStreamReader
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
@Order(2)
@RestController
class MockController {
    companion object {
        val log: Logger = LoggerFactory.getLogger(MockController::class.java)
        const val ROOT = "/"
    }

    @Value("\${mocker.uuid}")
    lateinit var uuid: String
    @Value("\${mocker.path.regex}")
    lateinit var  regex: String;


    @Autowired
    lateinit var repository: MockRepository;

    @Autowired
    lateinit var mapper: ObjectMapper

    @GetMapping("\${mocker.uuid}")
    fun getMapping(pageable: Pageable): Page<MockEntity> {
        return repository.findAll(pageable).map { it.body = null; it } // todo sql wo body
    }

    @GetMapping("uuid")
    fun getUUID(): Any {
        return mapOf("uuid" to uuid);
    }


    @PostMapping("\${mocker.uuid}")
    fun create(
            @RequestParam("path", required = true) path: String,
            @RequestParam("method", required = true) method: HttpMethod,
            @RequestParam("code", required = false, defaultValue = "200") code: Int? = 200,
            @RequestBody(required = false) json: String?): ResponseEntity<Any> {

        log.info("[CREATE] path: $path, method: $method, code: $code, body: $json")
        var path = path.trim()

        if (!regex.toRegex().matches(path)){
            return ResponseEntity.badRequest().body(mapOf("code" to "BAD_PATH", "message" to "Cant create mock method, PATH invalid"));
        }

        try {
            repository.save(MockEntity(
                    path = path,
                    code = code,
                    method = method.name,
                    body = json
            ))
        } catch (e: Exception) {
            log.error("cant save $path,$method $code $json", e)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
        return ResponseEntity.ok().build();
    }

    @RequestMapping("/{prefix}/{main}/**")
    fun allMapping(
            @PathVariable("prefix") prefix: String,
            @PathVariable("main") main: String,
            request: HttpServletRequest,
            response: HttpServletResponse
    ): ResponseEntity<Any> {
        log.info("[REQUEST] path=${request.requestURI}  method=${request.method} ")
        val mock = repository.findById(MockEntityId(request.requestURI, request.method)).orElse(null)
        log.info("[RESPONSE] mock: $mock ")


        return if (mock == null) {
            ResponseEntity.notFound().build();
        } else {
            var responseBody: Any? = null;
            if (mock.body != null) {
                responseBody = JSONParser(mock.body).parse();
            }

            ResponseEntity.status(mock.code ?: 200)
                    .body(responseBody)
        }

    }


}
