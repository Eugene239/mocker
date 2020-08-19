package ru.epavlov.mocker.entity

import ru.epavlov.mocker.entity.MockParam.Companion.TABLE_NAME
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = TABLE_NAME)
class MockParam(

        @Id
        @Column(name = ID)
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
        @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
        var id: Long? = null,

        @NotNull
        @Column(name = MOCK_ID)
        var mockId: Long? = null,

        @NotNull
        @Column(name = RESPONSE_ID, insertable = false, updatable = false)
        var responseId: Long? = null,

        @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @JoinColumn(name = Param.MOCK_PARAM_ID)
        val params: List<Param>? = emptyList(),

        @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @JoinColumn(name = MockResponse.ID)
        val response: MockResponse? = null
) {
    companion object {
        const val RESPONSE_ID = "RESPONSE_ID"
        const val MOCK_ID = "MOCK_ID"
        const val ID = "MOCK_PARAM_ID"
        const val SEQUENCE_NAME = "MOCK_PARAM_SEQUENCE"
        const val TABLE_NAME = "MOCK_PARAM"
    }

    override fun toString(): String {
        return "MockParam(id=$id, mockId=$mockId, responseId=$responseId)"
    }

}