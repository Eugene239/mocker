package ru.epavlov.mocker.repository

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Id

class MockEntityId(
        @Id
        @Column(name = "PATH")
        val path: String? = null,
        @Id
        @Column(name = "METHOD")
        val method: String? = null
) : Serializable {
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as MockEntityId

                if (path != other.path) return false
                if (method != other.method) return false

                return true
        }

        override fun hashCode(): Int {
                var result = path?.hashCode() ?: 0
                result = 31 * result + (method?.hashCode() ?: 0)
                return result
        }
}