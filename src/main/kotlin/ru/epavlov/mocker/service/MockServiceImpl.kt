package ru.epavlov.mocker.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import ru.epavlov.mocker.converter.MockConverter
import ru.epavlov.mocker.dto.*
import ru.epavlov.mocker.entity.ParamEntity
import ru.epavlov.mocker.entity.ParamType
import ru.epavlov.mocker.exception.ExceptionFabric
import ru.epavlov.mocker.repository.MockRepository
import ru.epavlov.mocker.repository.ParamRepository
import javax.transaction.Transactional

@Service
class MockServiceImpl(
        @Autowired val repository: MockRepository,
        @Autowired val paramRepository: ParamRepository,
        @Autowired val converter: MockConverter
) : MockService {

    companion object{
         val log: Logger  = LoggerFactory.getLogger(MockServiceImpl::class.java)
    }

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
    override fun getResponse(request: MockRequest): ParamsDTO? {
        val mock = repository.findAllByPathAndMethod(request.path, request.method) ?: return null
        val params = mock.params
        if (params.isNullOrEmpty()) return null

        var resultParam: ParamsDTO? = null

         params.forEach params@{param->
            val values = param.values
            values.forEach values@{ value ->
                if (ParamType.HEADER == value.type) {
                    val header = request.headers[value.name] ?: emptyList()
                    if (!header.contains(value.value)) return@params
                }
                if (ParamType.QUERY_PARAM == value.type){
                    val qParams = request.queryParams[value.name]?: emptyList()
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
    @Transactional
    override fun create(mock: MockDTO): MockDTO {
        var entity = converter.toEntity(mock)
        val exists = repository.findAllByPathAndMethod(mock.path, mock.method)
        if (exists != null) {
            exists.addParams(entity.params)
            entity = exists
        }
        entity = repository.save(entity)
        return converter.convertWOResponse(entity)
    }

    @Transactional // todo delete?
    override fun update(mock: MockDTO): MockDTO {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun delete(mockId: Long, param: ParamsDTO): MockDTO {
        log.info("delete param($mockId, $param)")
        val paramId = param.id ?: throw ExceptionFabric.IdRequired();
        val paramEntity = paramRepository.findByIdAndMockId(paramId, mockId)?: throw ExceptionFabric.paramNotFound()
        var mock = paramEntity.mock?: throw ExceptionFabric.mockNotFound()
        mock.params.remove(paramEntity)
        mock = repository.save(mock)
        return converter.convertWOResponse(mock)
    }

    @Transactional
    override fun delete(mockId: Long, paramId: Long, value: ParamValuesDTO): MockDTO {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun deleteResponse(mockId: Long, paramId: Long): MockDTO {
        log.info("delete response($mockId, $paramId");
        var paramEntity = paramRepository.findByIdAndMockId(paramId, mockId) ?: throw ExceptionFabric.paramNotFound()
        paramEntity.response = null
        paramEntity = paramRepository.save(paramEntity)
        return converter.convertWOResponse(paramEntity.mock!!)
    }

}