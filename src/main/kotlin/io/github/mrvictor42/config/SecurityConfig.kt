package io.github.mrvictor42.config

import io.github.mrvictor42.security.jwt.JwtConfigure
import io.github.mrvictor42.security.jwt.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.DelegatingPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig(private val tokenProvider: JwtTokenProvider) {

    @Bean
    fun passwordEncoder() : PasswordEncoder {
        val encoders : MutableMap<String, PasswordEncoder> = HashMap()
        val pbkdf2Encoder = Pbkdf2PasswordEncoder(
            "",
            8,
            185000,
            Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256
        )
        encoders["pbkdf2"] = pbkdf2Encoder

        val passwordEncoder = DelegatingPasswordEncoder("pbkdf2", encoders)

        passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder)

        return passwordEncoder
    }

    @Bean
    fun authenticationManagerBean(authenticationConfiguration : AuthenticationConfiguration) : AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun securityFilterChain(http : HttpSecurity) : SecurityFilterChain {
        return http
            .httpBasic().disable() // Isso é para dizer que a senha tem que ser encriptada
            .csrf { obj : CsrfConfigurer<HttpSecurity> -> obj.disable() }
            .sessionManagement {
                    session : SessionManagementConfigurer<HttpSecurity?> ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests { authorizeHttpRequests ->
                authorizeHttpRequests
                    .requestMatchers(
                        "/auth/signin",
                        "/auth/refresh/**",
                        "/v3/api-docs/**",
                        "/swagger-ui/**"
                    ).permitAll()
                    .requestMatchers("/api/**").authenticated()
                    .requestMatchers("/users").denyAll()
            }
            .cors()
            .and()
            .apply(JwtConfigure(tokenProvider))
            .and()
            .build()
    }
}