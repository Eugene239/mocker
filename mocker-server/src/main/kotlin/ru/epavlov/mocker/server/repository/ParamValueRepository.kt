package ru.epavlov.mocker.server.repository

import org.springframework.data.repository.CrudRepository
import ru.epavlov.mocker.server.entity.ParamValue

interface ParamValueRepository : CrudRepository<ParamValue,Long>{

}