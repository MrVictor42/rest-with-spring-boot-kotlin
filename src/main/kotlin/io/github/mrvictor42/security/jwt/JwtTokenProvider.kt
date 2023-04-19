package io.github.mrvictor42.security.jwt

import com.auth0.jwt.algorithms.Algorithm
import io.github.mrvictor42.data.vo.v1.TokenVO
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.Base64
import java.util.Date

@Service
class JwtTokenProvider(private val userDetailsService: UserDetailsService) {

    @Value("\${security.jwt.token.secret-key:secret}")
    private var securityKey = "secret"
    @Value("\${security.jwt.token.expire-length:3600000}")
    private val validityInMilliSeconds : Long = 3_600_000 // Equivale a 1h
    private lateinit var algorithm : Algorithm

    @PostConstruct
    protected fun init() {
        securityKey = Base64.getEncoder().encodeToString(securityKey.toByteArray())
        algorithm = Algorithm.HMAC256(securityKey.toByteArray())
    }

    fun createAccessToken(username : String, roles : List<String?>) : TokenVO {

        val now : Date = Date()
        val validity : Date = Date(now.time + validityInMilliSeconds)
        val accessToken = getAccessToken(username, roles, now, validity)
        val refreshToken = getRefreshToken(username, roles, now)

        return TokenVO(
            username = username,
            authenticated = true,
            accessToken = accessToken,
            refreshToken = refreshToken,
            created = now,
            expiration = validity
        )
    }

    private fun getRefreshToken(username: String, roles: List<String?>, now: Date): Any {

    }

    private fun getAccessToken(username: String, roles: List<String?>, now: Date, validity: Date): Any {

    }
}