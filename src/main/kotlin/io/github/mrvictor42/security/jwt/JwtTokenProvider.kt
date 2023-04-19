package io.github.mrvictor42.security.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import io.github.mrvictor42.data.vo.v1.TokenVO
import io.github.mrvictor42.exception.InvalidJwtAuthenticationExceptions
import jakarta.annotation.PostConstruct
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.Base64
import java.util.Date

@Service
class JwtTokenProvider(private val userDetailsService: UserDetailsService) {

    @Value("\${security.jwt.token.secret-key:secret}")
    private var secretKey = "secret"
    @Value("\${security.jwt.token.expire-length:3600000}")
    private val validityInMilliSeconds : Long = 3_600_000 // Equivale a 1h
    private lateinit var algorithm : Algorithm

    @PostConstruct
    protected fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
        algorithm = Algorithm.HMAC256(secretKey.toByteArray())
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

    private fun getAccessToken(username: String, roles: List<String?>, now: Date, validity: Date): String {
        val issuerURL : String = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()

        return JWT.create()
            .withClaim("roles", roles)
            .withIssuedAt(now)
            .withExpiresAt(validity)
            .withSubject(username)
            .withIssuer(issuerURL)
            .sign(algorithm)
            .trim()
    }

    private fun getRefreshToken(username: String, roles: List<String?>, now: Date): String {
        val validityRefreshToken : Date = Date(now.time + validityInMilliSeconds * 3) //refresh token valido por 3 horas

        return JWT.create()
            .withClaim("roles", roles)
            .withExpiresAt(validityRefreshToken)
            .withSubject(username)
            .sign(algorithm)
            .trim()
    }

    private fun getAuthentication(token : String) : Authentication {
        val decodedJWT : DecodedJWT = decodedToken(token)
        val userDetails : UserDetails = userDetailsService.loadUserByUsername(decodedJWT.subject)

        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    private fun decodedToken(token: String): DecodedJWT {
        val algorithm : Algorithm = Algorithm.HMAC256(secretKey.toByteArray())
        val verifier : JWTVerifier = JWT.require(algorithm).build()

        return verifier.verify(token)
    }

    private fun resolveToken(request : HttpServletRequest) : String? {
        val bearerToken : String = request.getHeader("Authorization")

        return if(!bearerToken.isNullOrBlank() && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring("Bearer ".length)
        } else {
            return null
        }
    }

    private fun validateToken(token : String) : Boolean {
        val decodedJWT : DecodedJWT = decodedToken(token)

        try {
            return !decodedJWT.expiresAt.before(Date())
        } catch (exception : Exception) {
            throw InvalidJwtAuthenticationExceptions("Expired or Invalid JWT Token!")
        }
    }
}