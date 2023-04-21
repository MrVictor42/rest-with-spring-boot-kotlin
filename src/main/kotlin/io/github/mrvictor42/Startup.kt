package io.github.mrvictor42

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBootRestKotlinApplication

fun main(args: Array<String>) {
	runApplication<SpringBootRestKotlinApplication>(*args)

/*	val encoders : MutableMap<String, PasswordEncoder> = HashMap()
	val pbkdf2Encoder = Pbkdf2PasswordEncoder("", 8, 18500, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256)
	encoders["pbkdf2"] = pbkdf2Encoder

	val passwordEncoder = DelegatingPasswordEncoder("pbkdf2", encoders)

	passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder)

	val result = passwordEncoder.encode("foo-bar")
	println("My hash $result")*/
}