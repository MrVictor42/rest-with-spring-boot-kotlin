package io.github.mrvictor42.services

import io.github.mrvictor42.data.vo.v1.AccountCredentialsVO
import io.github.mrvictor42.data.vo.v1.TokenVO
import io.github.mrvictor42.repository.UserRepository
import io.github.mrvictor42.security.jwt.JwtTokenProvider
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val authenticationManager: AuthenticationManager,
    private val tokenProvider: JwtTokenProvider
) {

    private val logger = Logger.getLogger(AuthService::class.java.name)

    fun signin(data : AccountCredentialsVO) : ResponseEntity<*> {
        logger.info("Trying log user ${data.username}")
        return try {
            val username : String? = data.username
            val password : String? = data.password

            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))

            val user = userRepository.findByUsername(username)
            val tokenResponse : TokenVO = if(user != null) {
                tokenProvider.createAccessToken(username!!, user.roles)
            } else {
                throw UsernameNotFoundException("Username $username not found!")
            }
            ResponseEntity.ok(tokenResponse)
        } catch (authenticationException : AuthenticationException) {
            throw BadCredentialsException("Invalid username or password supplied!")
        }
    }
}