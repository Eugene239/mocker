package ru.epavlov.mocker.server.exception

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.annotation.PropertySource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import ru.epavlov.mocker.dto.ErrorDTO
import java.util.*
import javax.persistence.EntityNotFoundException

@ControllerAdvice
@PropertySource(value = ["classpath:/messages/error.properties"], name = "error", encoding = "UTF-8")
class ErrorHandler @Autowired constructor(private val messageSource: MessageSource) : ResponseEntityExceptionHandler() {

    companion object {
        val log: Logger = LoggerFactory.getLogger(ErrorHandler::class.java)
    }

    @ExceptionHandler(ApiException::class)
    protected fun handleApiException(e: ApiException, locale: Locale): ResponseEntity<ErrorDTO> {
        val status = e.httpCode
        val code = e.messageCode
        var message: String? = null
        try {
            message = messageSource.getMessage(code.name, e.args.toTypedArray(), locale);
        } catch (ignored: Exception) {
            log.warn("missing arguments for code: ${e.messageCode}, args: ${e.args}");
        }
        return ResponseEntity(ErrorDTO(code.name, message), status)
    }
}