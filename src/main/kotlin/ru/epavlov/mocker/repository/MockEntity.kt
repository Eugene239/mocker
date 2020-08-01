package ru.epavlov.mocker.repository

import javax.persistence.*

@Table(name = "MOCK")
@Entity
@IdClass(MockEntityId::class)
data class MockEntity(
        @Id
        @Column(name = "PATH")
        var path: String? = null,
        @Id
        @Column(name = "METHOD")
        val method: String? = null,
        @Column(name = "BODY")
        var body: String? = null,
        @Column(name = "CODE")
        val code: Int? = 200
)


