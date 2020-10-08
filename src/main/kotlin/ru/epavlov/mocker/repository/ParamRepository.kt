package ru.epavlov.mocker.repository

import org.springframework.data.repository.CrudRepository
import ru.epavlov.mocker.entity.ParamEntity

interface ParamRepository: CrudRepository<ParamEntity, Long>{

}