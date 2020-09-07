package ru.epavlov.mocker.filter

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Needs to protect resource files from overriding
 */
@Component
class ResourceFilter : OncePerRequestFilter() {
    companion object {
        val log: Logger = LoggerFactory.getLogger(ResourceFilter::class.java)
    }

    @Autowired
    lateinit var resourceLoader: ResourceLoader

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val resource = ClassPathResource("static/${request.requestURI}")

        if (resource.exists()) {
            response.outputStream.apply {
                this.write(resource.inputStream.readBytes())
                this.flush()
                this.close()
            }
        } else {
            chain.doFilter(request, response)
        }
    }


}