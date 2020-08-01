package ru.epavlov.mocker.repository

import org.springframework.http.HttpMethod
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Id

data class MockEntityId(
        @Id
        @Column(name = "PATH")
        val path: String? = null,
        @Id
        @Column(name = "METHOD")
        val method: String? = null
) : Serializable