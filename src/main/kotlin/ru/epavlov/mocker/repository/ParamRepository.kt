package ru.epavlov.mocker.repository

import org.springframework.data.repository.CrudRepository
import ru.epavlov.mocker.entity.ParamEntity

interface ParamRepository: CrudRepository<ParamEntity, Long>{

     fun findByIdAndMockId(id: Long, mockId: Long): ParamEntity?
}