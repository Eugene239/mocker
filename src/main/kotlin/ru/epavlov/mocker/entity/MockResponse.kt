package ru.epavlov.mocker.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.http.HttpStatus
import ru.epavlov.mocker.converter.HttpStatusConverter
import ru.epavlov.mocker.entity.MockResponse.Companion.TABLE_NAME
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = TABLE_NAME)
@EntityListeners(AuditingEntityListener::class)
class MockResponse(

        @Id
        @Column(name = ID)
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
        @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
        val id: Long? = null,

        @Convert(converter = HttpStatusConverter::class)
        @Column(name = CODE, length = 7)
        val code: @NotNull HttpStatus? = null,

        @Column(name = BODY, length = 16387)
        val body: String? = null, //todo make json and add converter

        @Column(name = DELAY)
        val delay: Long? = null,

        @CreatedDate
        @Temporal(TemporalType.TIMESTAMP)
        var created: Date? = null,

        @LastModifiedDate
        @Temporal(TemporalType.TIMESTAMP)
        var updated: Date? = null
) {
    companion object {
        const val ID = "RESPONSE_ID"
        const val DELAY = "DELAY"
        const val CODE = "CODE"
        const val BODY = "BODY"
        const val TABLE_NAME = "RESPONSE"
        const val SEQUENCE_NAME = "RESPONSE_SEQUENCE"
    }
}