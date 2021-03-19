package ru.epavlov.mocker.server.config

import com.google.common.base.Predicates
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.epavlov.mocker.server.api.MockControllerImpl
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {


    @Bean
    fun api(): Docket? {
        return Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(
                Predicates.and(RequestHandlerSelectors.basePackage(MockControllerImpl::class.java.`package`.name))
            )
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo())
    }

    private fun apiInfo(): ApiInfo? {
        val libVersion = this.javaClass.getPackage().implementationVersion
        return ApiInfoBuilder()
            .title("Mocker REST API")
            .description("API describes Mocker API")
            .version(libVersion)
            .license("License of API")
            .build()
    }
}