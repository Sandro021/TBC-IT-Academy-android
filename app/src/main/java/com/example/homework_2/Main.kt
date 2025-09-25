package com.example.homework_2

import kotlin.random.Random

fun main() {
    calculations()
}


fun castAsNumber(property: String): Int {                       //cast string from integer as int
    val integer = property.filter { it.isDigit() }
    if (integer.isEmpty()) {
        return Random.nextInt(-127, 130)
    }
    return integer.toInt()
}

fun factorial(property: Int): Long {                                //counts factorial
    if (property < 0) {
        println("Negative number's factorial is not defined ")
        return -1
    }

    if (property <= 1) return 1
    return property * factorial(property - 1)
}


fun calculations() {
    while (true) {
        var x: String? = null
        var y: String? = null
        var operator: Char? = null
        while (x.isNullOrBlank()) {
            print("Please enter something: ")
            x = readLine()
        }
        while (y.isNullOrBlank()) {
            print("Please enter something: ")
            y = readLine()
        }
        while (operator == null) {
            print("Choose operator from this list: \"+\" , \"/\" , \"*\" , \"%\" , \"!\": ")
            val tmp = readLine()

            if (!tmp.isNullOrEmpty() && (tmp == "+" || tmp == "/" || tmp == "*"
                        || tmp == "%" || tmp == "!")
            ) {
                operator = tmp[0]
            } else {
                println("Input is empty or wrong operator try again!!! ")
            }
        }

        val xAsDigit = castAsNumber(x)
        val yAsDigit = castAsNumber(y)                                                        //this functions calculates operations
        val sum = xAsDigit + yAsDigit
        val multiply = xAsDigit * yAsDigit
        val reminder = if(yAsDigit != 0) xAsDigit % yAsDigit else null
        val resultFactorial = if(yAsDigit != 0) factorial(xAsDigit / yAsDigit) else null
        val divide = if(yAsDigit != 0) xAsDigit.toDouble() / yAsDigit.toDouble() else null

        when (operator) {
            '+' -> println("$xAsDigit and $yAsDigit $operator is: $sum ")
            '/' -> {
                if(divide == null) println("Cant divide on 0")
                else {
                    println("$xAsDigit and $yAsDigit $operator is: $divide ")
                }
            }

            '*' -> println("$xAsDigit and $yAsDigit $operator is: $multiply ")
            '%' -> {
                if(reminder == null) println("Cant divide on 0")
                else {
                    println("$xAsDigit and $yAsDigit $operator is: $reminder ")
            }
            }
            '!' -> {
                if(resultFactorial == null) println("Cant divide on 0")
                else {
                    println("$xAsDigit and $yAsDigit $operator is: $resultFactorial ")
                }

            }
        }


        print("Do you want to start again <Y/N>: ")
        val answer = readLine()
        if (answer == "N" || answer == "n") break
        if (answer == "Y" || answer == "y") continue
        else {
            println("No valid answer: ")
            break
        }

    }

}

