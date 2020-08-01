package ru.epavlov.mocker.repository

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface MockRepository: PagingAndSortingRepository<MockEntity, MockEntityId> {
}