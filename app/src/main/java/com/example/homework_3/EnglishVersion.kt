package com.example.homework_3

fun main() {
    println(writeWithWordsEng(937))
}
val underTwentyForEnglish = arrayOf("", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
    "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen",
    "seventeen", "eighteen", "nineteen")

val wholeNumbers = arrayOf( "", "", "twenty", "thirty", "forty", "fifty", "sixty"
    , "seventy", "eighty", "ninety")

fun writeWithWordsEng(num : Int) : String {
    if(num == 0) return "zero"
    if(num == 1000) return "thousand"
    if(num < 20) return underTwentyForEnglish[num]
    if(num < 100) return wholeNumbers[num / 10] + if(num % 10 != 0)   underTwentyForEnglish[num % 10] else ""
    if(num < 1000) return underTwentyForEnglish[num / 100] + "hundred" + if(num % 100 != 0) " " +
            writeWithWordsEng(num % 100) else ""
    return "Please put number from 0 to 1000"
}