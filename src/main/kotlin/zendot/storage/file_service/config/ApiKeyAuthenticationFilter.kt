package zendot.storage.file_service.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import zendot.storage.file_service.config.repository.DomainBucketRepository
@Component
class ApiKeyAuthenticationFilter(
    private val domainBucketRepository: DomainBucketRepository,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val secret = request.getHeader("Authorization")  // Use standard header
        val domainValueModel=domainBucketRepository.findBySecret(secret)
        if (domainValueModel != null) {
            val authentication = UsernamePasswordAuthenticationToken(domainValueModel,null, emptyList())
            val context: SecurityContext = SecurityContextHolder.createEmptyContext()
            context.authentication = authentication
            SecurityContextHolder.setContext(context)
        }
        filterChain.doFilter(request, response)
    }
}