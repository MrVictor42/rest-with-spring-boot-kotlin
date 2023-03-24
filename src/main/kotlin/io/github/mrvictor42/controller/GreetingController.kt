package io.github.mrvictor42.controller

import io.github.mrvictor42.model.Greeting
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

@RestController
@RequestMapping("/greeting")
class GreetingController {

    val counter : AtomicLong = AtomicLong()

    @GetMapping("")
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name : String?) : Greeting {
        return Greeting(counter.incrementAndGet(), "Hello, $name!")
    }
}