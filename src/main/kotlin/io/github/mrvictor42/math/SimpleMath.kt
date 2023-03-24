package io.github.mrvictor42.math

import io.github.mrvictor42.converters.NumberConverter

class SimpleMath {

    fun sum(numberOne : Double, numberTwo : Double) : Double {
        return numberOne + numberTwo
    }

    fun sub(numberOne : Double, numberTwo : Double) : Double {
        return numberOne - numberTwo
    }

    fun multi(numberOne : Double, numberTwo : Double) : Double {
        return numberOne * numberTwo
    }

    fun div(numberOne : Double, numberTwo : Double) : Double {
        return numberOne / numberTwo
    }

    fun mean(numberOne: Double, numberTwo: Double) : Double {
        return (numberOne + numberTwo) / 2
    }

    fun sqrt(number : Double) : Double {
        return sqrt(number)
    }
}