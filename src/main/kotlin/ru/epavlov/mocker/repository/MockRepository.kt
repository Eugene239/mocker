package ru.epavlov.mocker.repository

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Repository
import ru.epavlov.mocker.entity.MockEntity

@Repository
interface MockRepository: PagingAndSortingRepository<MockEntity, Long> {

    // exist check
    fun findAllByMethodAndPath(method: HttpMethod, path: String): List<MockEntity>
}

