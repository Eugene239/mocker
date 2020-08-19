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
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
        @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
        val id: Long? = null,

        @NotNull
        @Column(name = PATH)
        var path: String? = null,

        @NotNull
        @Column(name = METHOD)
        val method: HttpMethod? = null,

        @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @JoinColumn(name = MockParam.MOCK_ID)
        val mockParams: MutableList<MockParam>? = mutableListOf(),

        @ManyToMany(cascade = [CascadeType.ALL])
        @JoinTable(
                name = MockParam.TABLE_NAME,
                joinColumns = [JoinColumn(name=MockParam.MOCK_ID)],
                inverseJoinColumns = [JoinColumn(name=Param.ID)]
        )
        val params: MutableList<Param>? = mutableListOf(),

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
        const val SEQUENCE_NAME = "mock_sequence"
    }

    override fun toString(): String {
        return "MockEntity(id=$id, path=$path, method=$method, created=$created, updated=$updated)"
    }

}
