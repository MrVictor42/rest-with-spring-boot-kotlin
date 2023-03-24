package io.github.mrvictor42

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBootRestKotlinApplication

fun main(args: Array<String>) {
	runApplication<SpringBootRestKotlinApplication>(*args)
}