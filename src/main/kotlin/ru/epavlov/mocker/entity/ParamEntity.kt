package ru.epavlov.mocker.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.http.HttpStatus
import ru.epavlov.mocker.converter.HttpStatusConverter
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = ParamEntity.TABLE_NAME)
@EntityListeners(AuditingEntityListener::class)
class ParamEntity(
        @Id
        @Column(name = ID)
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
        @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
        var id: Long? = null,

        @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @JoinColumn(name = MockResponse.ID)
        var response: MockResponse? = null,

        @Convert(converter = HttpStatusConverter::class)
        @Column(name = CODE, length = 7)
        val code: @NotNull HttpStatus? = null,

        @Column(name = DELAY)
        val delay: Long? = null,

        @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
        @JoinColumn(name = ID, referencedColumnName = ID)
        var values : MutableList<ParamValue> = ArrayList(),

        @CreatedDate
        @Temporal(TemporalType.TIMESTAMP)
        var created: Date? = null,

        @LastModifiedDate
        @Temporal(TemporalType.TIMESTAMP)
        var updated: Date? = null

) {
    companion object {
        //const val RESPONSE_ID = "RESPONSE_ID"
        const val MOCK_ID = "MOCK_ID"
        const val ID = "PARAM_ID"
        const val DELAY = "DELAY"
        const val CODE = "CODE"
        const val SEQUENCE_NAME = "PARAM_SEQUENCE"
        const val TABLE_NAME = "PARAM"
    }

    override fun toString(): String {
        return "Param(id=$id)"
    }

}