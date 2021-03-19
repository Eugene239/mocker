package ru.epavlov.mocker.server.converter

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class HttpStatusConverter : AttributeConverter<HttpStatus?, Int?> {
    companion object {
        val log: Logger = LoggerFactory.getLogger(HttpStatusConverter::class.java)
    }

    override fun convertToDatabaseColumn(httpStatus: HttpStatus?): Int? {
        httpStatus?.let {
            return it.value()
        }
        return null
    }

    override fun convertToEntityAttribute(s: Int?): HttpStatus? {
        s?.let {
            try {
                return HttpStatus.valueOf(s)
            } catch (e: Exception) {
                log.error("cant convert $s", e)
            }
        }
        return null
    }
}