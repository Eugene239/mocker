package ru.epavlov.mocker.repository

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import ru.epavlov.mocker.entity.MockEntity

@Repository
interface MockRepository: PagingAndSortingRepository<MockEntity, Long> {}

