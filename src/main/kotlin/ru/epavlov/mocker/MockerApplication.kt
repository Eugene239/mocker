package ru.epavlov.mocker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.servlet.config.annotation.EnableWebMvc

//@EnableWebMvc
@SpringBootApplication
class MockerApplication

fun main(args: Array<String>) {
    runApplication<MockerApplication>(*args)
}
