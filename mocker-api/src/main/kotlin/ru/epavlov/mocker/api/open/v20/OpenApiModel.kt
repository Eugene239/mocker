package ru.epavlov.mocker.api.open.v20

/**
 * https://petstore.swagger.io/v2/swagger.json
 * https://editor.swagger.io/
 */
data class OpenApiModel(
    val basePath: String? = "",
    val paths: Map<String /* path */, Map<String /* method*/, OpenApiMethod>>? = emptyMap(),
    val definitions: Map<String,  OpenApiDefinition>? = emptyMap()
)