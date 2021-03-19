package ru.epavlov.mocker.api.dto

enum class ParamType {
    HEADER,
    QUERY_PARAM,
    /*
        (!) to lower case
        for read
        /ver/mobihub23/store/123
        \/ver\/([a-z0-9]*)\/store\/([a-z0-9]*)


        for regexp
        \$([a-z0-9]*)
        /ver/$mobihub/store/$storeId

        Group = QueryParam

     */
    PATH_PARAM // hmmm

}