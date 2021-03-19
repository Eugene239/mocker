package ru.epavlov.mocker.server.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.http.HttpMethod
import ru.epavlov.mocker.entity.MockEntity.Companion.TABLE_NAME
import ru.epavlov.mocker.entity.MockEntity.Companion.UQ_CONSTRAINT
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Table(name = TABLE_NAME,
        uniqueConstraints = [UniqueConstraint(name = UQ_CONSTRAINT, columnNames = [MockEntity.PATH, MockEntity.METHOD])])
@Entity(name = TABLE_NAME)
@EntityListeners(AuditingEntityListener::class)
open class MockEntity(

        @Id
        @Column(name = ID)
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
        @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
        open var id: Long? = null,

        @Column(name = PATH, length = 255)
        open var path: @NotNull String ,

        @Enumerated(EnumType.STRING)
        @Column(name = METHOD, length = 15)
        open var method: @NotNull HttpMethod,

        @CreatedDate
        @Temporal(TemporalType.TIMESTAMP)
        open var created: Date? = null,

        @LastModifiedDate
        @Temporal(TemporalType.TIMESTAMP)
        open var updated: Date? = null,

        @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
        @JoinColumn(name = ParamEntity.MOCK_ID, referencedColumnName = ID)
        open var params: MutableList<ParamEntity> = ArrayList()
) {


    companion object {
        const val METHOD = "METHOD"
        const val PATH = "PATH"
        const val ID = "ID"
        const val TABLE_NAME = "MOCK"
        const val UQ_CONSTRAINT = "MOCK_ENTITY_UQ"
        const val SEQUENCE_NAME = "MOCK_SEQUENCE"
    }

    fun addParam(paramEntity: ParamEntity){
        paramEntity.mock = this
        this.params.add(paramEntity)
    }

    fun addParams(params: List<ParamEntity>){
        params.forEach {
            addParam(it)
        }
    }
}