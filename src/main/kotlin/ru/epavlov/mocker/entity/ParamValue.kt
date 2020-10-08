package ru.epavlov.mocker.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import ru.epavlov.mocker.entity.ParamValue.Companion.TABLE_NAME
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = TABLE_NAME)
@EntityListeners(AuditingEntityListener::class)
open class ParamValue(

        @Id
        @Column(name = ID)
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
        @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
        open var id: Long? = null,

        @Column(name = NAME, length = 255)
        open var name: @NotNull String,

        @Enumerated(EnumType.STRING)
        @Column(name = TYPE, length = 255)
        open var type: @NotNull ParamType,

        @Column(name = VALUE, length = 255)
        open var value: @NotNull String ,

        @ManyToOne(fetch = FetchType.LAZY)
        open var paramEntity: ParamEntity? = null,

        @CreatedDate
        @Temporal(TemporalType.TIMESTAMP)
        open var created: Date? = null,

        @LastModifiedDate
        @Temporal(TemporalType.TIMESTAMP)
        open var updated: Date? = null
) {
    companion object {
        const val ID = "PARAM_VALUE_ID"
        const val TABLE_NAME = "PARAM_VALUE"

        //const val MOCK_PARAM_ID = "MOCK_PARAM_ID"
        const val TYPE = "TYPE"
        const val VALUE = "VALUE"
        const val NAME = "NAME"
        const val SEQUENCE_NAME = "PARAM_VALUE_SEQUENCE"
    }

    override fun toString(): String {
        return "ParamValue(id=$id, name='$name', type=$type, value='$value', created=$created, updated=$updated)"
    }


}