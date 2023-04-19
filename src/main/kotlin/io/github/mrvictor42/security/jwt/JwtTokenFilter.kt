package io.github.mrvictor42.security.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean

class JwtTokenFilter(private val tokenProvider: JwtTokenProvider) : GenericFilterBean() {

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain) {
        val token : String? = tokenProvider.resolveToken(request as HttpServletRequest)

        if(!token.isNullOrBlank() && tokenProvider.validateToken(token)) {
            val auth : Authentication = tokenProvider.getAuthentication(token)

            SecurityContextHolder.getContext().authentication = auth
        }
        chain.doFilter(request, response)
    }
}