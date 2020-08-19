package ru.epavlov.mocker.entity

import org.springframework.http.HttpStatus
import javax.persistence.*

@Entity
@Table(name = "RESPONSE")
class MockResponse(

        @Id
        @Column(name = ID)
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
        @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
        val id: Long? = null,

        @Column(name = CODE)
        val code: HttpStatus? = null,

        @Column(name = DELAY)
        val delay: Long? = null
) {
    companion object {
        const val ID = "RESPONSE_ID"
        const val DELAY = "DELAY"
        const val CODE = "CODE"
        const val SEQUENCE_NAME = "SEQUENCE_NAME"
    }
}