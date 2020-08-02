package ru.epavlov.mocker.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface MockRepository: PagingAndSortingRepository<MockEntity, MockEntityId> {

    @Query(nativeQuery = true, value = SQL_ALL_WO_BODY, countQuery = SQL_COUNT_ALL_WO_BODY)
    fun findAllWOBody(pageable: Pageable): Page<MockEntity>
}

const val SQL_ALL_WO_BODY  = """
    SELECT PATH, METHOD, CODE, CREATED, null as BODY
    FROM MOCK
    ORDER BY CREATED DESC
"""

const val SQL_COUNT_ALL_WO_BODY  = """
    SELECT COUNT(*) count
    FROM MOCK
"""

