package ru.epavlov.mocker.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import ru.epavlov.mocker.converter.MockConverter
import ru.epavlov.mocker.dto.MockDTO
import ru.epavlov.mocker.dto.ParamValuesDTO
import ru.epavlov.mocker.dto.ParamsDTO
import ru.epavlov.mocker.dto.ResponseDTO
import ru.epavlov.mocker.entity.ParamType
import ru.epavlov.mocker.repository.MockRepository
import javax.transaction.Transactional

@Service
class MockServiceImpl(
        @Autowired val repository: MockRepository,
        @Autowired val converter: MockConverter
) : MockService {

    override fun getMocks(pageable: Pageable): Page<MockDTO> {
        return repository.findAll(pageable).map { converter.convertWOParams(it) }
    }

    @Transactional
    @Throws
    override fun getMock(id: Long): MockDTO? {
        val mock = repository.findById(id).orElseThrow { ChangeSetPersister.NotFoundException() }
        return converter.convertWOResponse(mock)
    }

    @Transactional
    override fun getResponse(path: String, method: HttpMethod, queryParams: Map<String, List<String>>, headers: Map<String, List<String>>): ParamsDTO? {
        val mock = repository.findAllByPathAndMethod(path, method) ?: return null
        val params = mock.params
        if (params.isNullOrEmpty()) return null

        var resultParam: ParamsDTO? = null

         params.forEach params@{param->
             println("checkParam $param")
            val values = param.values
            values.forEach values@{ value ->
                println("checkValue $value")
                if (ParamType.HEADER == value.type) {
                    val header = headers[value.name] ?: emptyList()
                    if (!header.contains(value.value)) return@params
                }
                if (ParamType.QUERY_PARAM == value.type){
                    val qParams = queryParams[value.name]?: emptyList()
                    if (!qParams.contains(value.value)) return@params
                }
            }
            resultParam = converter.toDTO(param)
        }

        return resultParam
    }


    /**
     * Return information about mock without response data
     */
    override fun create(mock: MockDTO): MockDTO {
        var entity = converter.toEntity(mock)
        entity = repository.save(entity)
        return converter.convertWOResponse(entity)
    }

    override fun update(mock: MockDTO): MockDTO {
        TODO("Not yet implemented")
    }

    override fun delete(param: ParamsDTO): MockDTO {
        TODO("Not yet implemented")
    }

    override fun delete(value: ParamValuesDTO): MockDTO {
        TODO("Not yet implemented")
    }

    override fun delete(response: ResponseDTO): MockDTO {
        TODO("Not yet implemented")
    }


}