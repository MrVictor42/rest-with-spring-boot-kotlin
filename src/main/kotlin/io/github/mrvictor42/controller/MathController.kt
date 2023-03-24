package io.github.mrvictor42.controller

import io.github.mrvictor42.converters.NumberConverter
import io.github.mrvictor42.exception.UnsupportedMathOperationException
import io.github.mrvictor42.math.SimpleMath
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.math.sqrt

@RestController
@RequestMapping("/math")
class MathController {

    private val math = SimpleMath()

    @GetMapping("/sum/{numberOne}/{numberTwo}")
    fun sum(@PathVariable(value="numberOne") numberOne: String?, @PathVariable(value="numberTwo") numberTwo: String?): Double {
        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
            throw UnsupportedMathOperationException("Please set a numeric value!")
        }
        return math.sum(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo))
    }

    @GetMapping("/sub/{numberOne}/{numberTwo}")
    fun sub(@PathVariable(value="numberOne") numberOne: String?, @PathVariable(value="numberTwo") numberTwo: String?): Double {
        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
            throw UnsupportedMathOperationException("Please set a numeric value!")
        }
        return math.sub(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo))
    }

    @GetMapping("/multi/{numberOne}/{numberTwo}")
    fun multi(@PathVariable(value="numberOne") numberOne: String?, @PathVariable(value="numberTwo") numberTwo: String?): Double {
        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
            throw UnsupportedMathOperationException("Please set a numeric value!")
        }
        return math.multi(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo))
    }

    @GetMapping("/div/{numberOne}/{numberTwo}")
    fun div(@PathVariable(value="numberOne") numberOne: String?, @PathVariable(value="numberTwo") numberTwo: String?): Double {
        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
            throw UnsupportedMathOperationException("Please set a numeric value!")
        }
        return math.div(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo))
    }

    @GetMapping("/mean/{numberOne}/{numberTwo}")
    fun mean(@PathVariable(value="numberOne") numberOne: String?, @PathVariable(value="numberTwo") numberTwo: String?): Double {
        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
            throw UnsupportedMathOperationException("Please set a numeric value!")
        }
        return math.mean(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo))
    }

    @GetMapping("/squareRoot/{number}")
    fun squareRoot(@PathVariable(value="number") number: String?): Double {
        if (!NumberConverter.isNumeric(number)) {
            throw UnsupportedMathOperationException("Please set a numeric value!")
        }
        return math.sqrt(NumberConverter.convertToDouble(number))
    }
}