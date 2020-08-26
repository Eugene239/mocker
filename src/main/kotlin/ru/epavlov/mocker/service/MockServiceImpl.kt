package ru.epavlov.mocker.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import ru.epavlov.mocker.converter.MockConverter
import ru.epavlov.mocker.dto.MockDTO
import ru.epavlov.mocker.dto.ParamValuesDTO
import ru.epavlov.mocker.dto.ParamsDTO
import ru.epavlov.mocker.dto.ResponseDTO
import ru.epavlov.mocker.repository.MockRepository
import javax.servlet.http.HttpServletRequest

@Service
class MockServiceImpl(
        @Autowired val repository: MockRepository,
        @Autowired val converter: MockConverter
) : MockService {

    override fun getMocks(pageable: Pageable): Page<MockDTO> {
        return repository.findAll(pageable).map { converter.convertWOParams(it) }
    }

    @Throws //TODO
    override fun getMock(id: Long): MockDTO? {
        val mock = repository.findById(id).orElseThrow { ChangeSetPersister.NotFoundException() }
        return converter.convertWOResponse(mock)
    }

    override fun getResponse(request: HttpServletRequest): ResponseEntity<Any> {
        TODO("Not yet implemented")
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