package ru.epavlov.mocker.server.exception

enum class ErrorCode {
    UNKNOWN_ERROR,
    MOCK_NOT_FOUND,
    WRONG_MOCK_ID,
    WRONG_PARAM_ID,
    PARAM_NOT_FOUND,
    PARAM_VALUE_NOT_FOUND,
    ID_REQUIRED
}