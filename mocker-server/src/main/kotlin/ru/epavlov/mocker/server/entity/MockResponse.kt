package ru.epavlov.mocker.server.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import ru.epavlov.mocker.server.entity.MockResponse.Companion.TABLE_NAME
import java.util.*
import javax.persistence.*

@Entity
@Table(name = TABLE_NAME)
@EntityListeners(AuditingEntityListener::class)
open class MockResponse(

        @Id
        @Column(name = ID)
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
        @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
        open var id: Long? = null,

        @Column(name = BODY, length = 16387)
        open var body: String? = null, //todo make json and add converter?

        @CreatedDate
        @Temporal(TemporalType.TIMESTAMP)
        open var created: Date? = null,

        @LastModifiedDate
        @Temporal(TemporalType.TIMESTAMP)
        open var updated: Date? = null
) {
    companion object {
        const val ID = "RESPONSE_ID"
        const val BODY = "BODY"
        const val TABLE_NAME = "RESPONSE"
        const val SEQUENCE_NAME = "RESPONSE_SEQUENCE"
    }
}