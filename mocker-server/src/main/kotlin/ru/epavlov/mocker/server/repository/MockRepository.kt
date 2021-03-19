package ru.epavlov.mocker.server.repository

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Repository
import ru.epavlov.mocker.server.entity.MockEntity

@Repository
interface MockRepository: PagingAndSortingRepository<MockEntity, Long> {

    // exist check
    fun findAllByPathAndMethod(path: String, method: HttpMethod): MockEntity?
}

