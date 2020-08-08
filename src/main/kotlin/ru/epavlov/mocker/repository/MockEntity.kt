package ru.epavlov.mocker.repository

import java.time.OffsetDateTime
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
        val code: Int? = 200,
        @Column(name = "CREATED")
        val created: OffsetDateTime? = OffsetDateTime.now()
) {
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as MockEntity

                if (path != other.path) return false
                if (method != other.method) return false
                if (body != other.body) return false
                if (code != other.code) return false
                if (created != other.created) return false

                return true
        }

        override fun hashCode(): Int {
                var result = path?.hashCode() ?: 0
                result = 31 * result + (method?.hashCode() ?: 0)
                result = 31 * result + (body?.hashCode() ?: 0)
                result = 31 * result + (code ?: 0)
                result = 31 * result + (created?.hashCode() ?: 0)
                return result
        }
}


