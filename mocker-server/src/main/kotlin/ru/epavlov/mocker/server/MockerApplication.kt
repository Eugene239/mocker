package ru.epavlov.mocker.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class MockerApplication  {}

fun main(args: Array<String>) {
    runApplication<MockerApplication>(*args)
}
