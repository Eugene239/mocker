package ru.epavlov.mocker.entity

import javax.persistence.*

//@Entity
//@Table(name = "PARAM")
//class ParamValue(
//
//        @Id
//        @Column(name = ID)
//        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
//        @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
//        var id: Long? = null,
//
//        @Column(name = MOCK_PARAM_ID)
//        var mockParamId: Long? = null,
//
//        @ManyToMany(mappedBy = "params")
//        val mocks: List<MockEntity>? = emptyList(),
//
//        @Column(name = NAME)
//        var name: String? = null,
//
//        @Column(name = TYPE)
//        var type: ParamType? = null,
//
//        @Column(name = VALUE)
//        var value: String? = null
//
//) {
//    companion object {
//        const val ID = "PARAM_ID"
//        const val MOCK_PARAM_ID = "MOCK_PARAM_ID"
//        const val TYPE = "TYPE"
//        const val VALUE = "VALUE"
//        const val NAME = "NAME"
//        const val SEQUENCE_NAME = "PARAM_SEQUENCE"
//    }
//}