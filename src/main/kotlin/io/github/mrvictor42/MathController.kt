package io.github.mrvictor42

import io.github.mrvictor42.exception.UnsupportedMathOperationException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/math")
class MathController {

    @GetMapping("/sum/{numberOne}/{numberTwo}")
    fun sum(@PathVariable(value="numberOne") numberOne: String?, @PathVariable(value="numberTwo") numberTwo: String?): Double {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw UnsupportedMathOperationException("Please set a numeric value!")
        }
        return convertToDouble(numberOne) + convertToDouble(numberTwo)
    }

    private fun convertToDouble(strNumber: String?): Double {
        if (strNumber.isNullOrBlank()) {
            return 0.0
        }
        val number = strNumber.replace(",".toRegex(), ".")
        return if (isNumeric(number)) number.toDouble() else 0.0
    }

    private fun isNumeric(strNumber: String?): Boolean {
        if (strNumber.isNullOrBlank()) {
            return false
        }
        val number = strNumber.replace(",".toRegex(), ".")
        return number.matches("""[-+]?[0-9]*\.?[0-9]+""".toRegex())
    }
}