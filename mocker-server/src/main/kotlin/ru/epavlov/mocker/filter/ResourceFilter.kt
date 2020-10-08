package ru.epavlov.mocker.filter

import org.springframework.core.io.ClassPathResource
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