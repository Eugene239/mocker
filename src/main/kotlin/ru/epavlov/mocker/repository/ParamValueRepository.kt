package ru.epavlov.mocker.repository

import org.springframework.data.repository.CrudRepository
import ru.epavlov.mocker.entity.ParamValue

interface ParamValueRepository : CrudRepository<ParamValue,Long>{

}