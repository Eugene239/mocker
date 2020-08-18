package ru.epavlov.mocker.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.http.HttpMethod
import ru.epavlov.mocker.entity.MockEntity.Companion.METHOD
import ru.epavlov.mocker.entity.MockEntity.Companion.PATH
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Table(name = "MOCK",
        uniqueConstraints = [
            UniqueConstraint(name = "MOCK_ENTITY_UQ", columnNames = [PATH, METHOD])
        ]
)
@Entity
@EntityListeners(AuditingEntityListener::class)
class MockEntity(

        @Id
        @Column(name = ID)
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shopping_list_sequence")
        @SequenceGenerator(name = "shopping_list_sequence", sequenceName = "shopping_list_sequence", allocationSize = 1)
        val id: Long? = null,

        @NotNull
        @Column(name = PATH)
        var path: String? = null,

        @NotNull
        @Column(name = METHOD)
        val method: HttpMethod? = null,

        @CreatedDate
        @Temporal(TemporalType.TIMESTAMP)
        var created: Date? = null,

        @LastModifiedDate
        @Temporal(TemporalType.TIMESTAMP)
        var updated: Date? = null
) {
    companion object {
        const val METHOD = "METHOD"
        const val PATH = "PATH"
        const val ID = "ID"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MockEntity

        if (id != other.id) return false
        if (path != other.path) return false
        if (method != other.method) return false
        if (created != other.created) return false
        if (updated != other.updated) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (path?.hashCode() ?: 0)
        result = 31 * result + (method?.hashCode() ?: 0)
        result = 31 * result + (created?.hashCode() ?: 0)
        result = 31 * result + (updated?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "MockEntity(id=$id, path=$path, method=$method, created=$created, updated=$updated)"
    }

}
