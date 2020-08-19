package ru.epavlov.mocker.entity

import javax.persistence.*

@Entity
@Table(name = Param.TABLE_NAME)
class Param {
    @Id
    @Column(name = ID)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    var id: Long? = null

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = MockResponse.ID)
    var response: MockResponse? = null

    companion object {
        const val RESPONSE_ID = "RESPONSE_ID"
        const val MOCK_ID = "MOCK_ID"
        const val ID = "PARAM_ID"
        const val SEQUENCE_NAME = "PARAM_SEQUENCE"
        const val TABLE_NAME = "PARAM"
    }
}