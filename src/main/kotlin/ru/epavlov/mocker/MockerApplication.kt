package ru.epavlov.mocker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MockerApplication

fun main(args: Array<String>) {
    runApplication<MockerApplication>(*args)
}
