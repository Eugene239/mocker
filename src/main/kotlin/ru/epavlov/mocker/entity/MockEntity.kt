package ru.epavlov.mocker.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.http.HttpMethod
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Table(name = "MOCK", uniqueConstraints = [UniqueConstraint(name = "MOCK_ENTITY_UQ", columnNames = [MockEntity.PATH, MockEntity.METHOD])])
@Entity
@EntityListeners(AuditingEntityListener::class)
class MockEntity {

    @Id
    @Column(name = ID)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    var id: Long? = null

    @Column(name = PATH)
    var path: @NotNull String? = null

    @Enumerated(EnumType.STRING)
    @Column(name = METHOD)
    var method: @NotNull HttpMethod? = null

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    var created: Date? = null

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    var updated: Date? = null

    @OneToMany(cascade = [CascadeType.ALL],   fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = Param.MOCK_ID, referencedColumnName = ID)
    var params: MutableList<Param> = ArrayList()

    constructor()
    constructor(path: String?, method: HttpMethod?) {
        this.path = path
        this.method = method
    }


    companion object {
        const val METHOD = "METHOD"
        const val PATH = "PATH"
        const val ID = "ID"
        const val SEQUENCE_NAME = "MOCK_SEQUENCE"
    }
}