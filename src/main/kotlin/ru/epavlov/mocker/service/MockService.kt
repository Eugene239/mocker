package ru.epavlov.mocker.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.epavlov.mocker.repository.MockRepository

@Service
class MockService {


    @Autowired
    lateinit var repository: MockRepository


}