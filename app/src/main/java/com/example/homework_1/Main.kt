package com.example.homework_1

fun main() {

    val sum =  sumEvenNumbers(0 , 0)
    println(sum)
    println(containsSymbol(text = "sk\$ami"))
    println(gcd(48 , 12))
    println(lcm(7,43))
    println(reverseNumber(30245))
    println(isPalindrome("skami"))
}

fun sumEvenNumbers(num : Int , res : Int) : Int {
    if(num == 100) {                                    // when number is 100 recursion ends and add 100 to a res
        return res + 100
    }

    return sumEvenNumbers(num + 2, res + num)       // increase num by 2 and res increases by num 
}

fun containsSymbol(text: String) : Boolean {
    for(ch in text) {                               //iterate with for loop if it finds symbol return true
        if(ch == '$') return true
    }

    return false;
}

fun gcd(a : Int , b : Int) : Int {
    if(a > b) return gcd(b , a)             // if first number is larger than second change orders
    if(b % a == 0) return a                         // if reminder is zero it means gcd is exactly smaller number

    return gcd(b % a , a)                   // this is euclidian algorithm for finding gcd with reminders

}
fun lcm(a : Int , b : Int) : Int {                  // lcm is a * b divided on gcd
    return (a * b) / gcd(a,b)
}

fun reverseNumber( n : Int) : Int {
    var a = n                                      // with reminder you get last number
    var res = 0                                    // than add this number to a result and multiply by 10
    while(a != 0) {                                // till given number is 0
        val tmp = a % 10
        res = res * 10 + tmp
        a  = a/ 10
    }
    return res
}

fun isPalindrome(text : String) : Boolean {           // in this problem I ignore whitespaces and any character
    var reversed : String = ""                        // that is not letter from a to z and cast everything into lowercase
    var newText : String = ""
    val lowerCaseText = text.lowercase()

    for(ch in lowerCaseText) {
        if(ch < 'a' || ch > 'z') continue
        newText += ch
    }
    for(i in newText.indices.reversed()) {          // reverse it with for loop iterate from the end
        reversed += newText[i]
    }

    return reversed.lowercase() == newText.lowercase()
}