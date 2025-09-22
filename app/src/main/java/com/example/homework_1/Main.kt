package com.example.homework_1

fun main() {

   // val sum =  sumEvenNumbers(0 , 0)
   // println(sum)
    println(containsSymbol(text = "sk\$ami"))
}

fun sumEvenNumbers(num : Int , res : Int) : Int {
    if(num == 100) {
        return res + 100
    }

    return sumEvenNumbers(num + 2, res + num)
}

fun containsSymbol(text: String) : Boolean {
    for(ch in text) {
        if(ch == '$') return true
    }

    return false;

}