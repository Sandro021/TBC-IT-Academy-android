package com.example.homework_3

fun main() {
    println(writeWithWords(925))
}

val underTwenty = arrayOf("" , "erti" , "ori" , "sami" , "otxi" , "xuti" , "eqvsi" , "shvidi" ,
        "rva" , "cxra" , "ati" , "tertmeti" , "tormeti" , "cameti" , "totxmeti" , "txutmeti" ,
        "teqvsmeti" , "chvidmeti" , "tvrameti" , "cxrameti")


val wholeNums = arrayOf("" , "" , "ati" , "oc"  , "ormoc","ormoc", "samoc" , "samoc",
          "otxmoc", "otxmoc")

val hundreds = arrayOf("", "as" , "oras" , "samas" , "otxas" , "xutas" , "eqvsas" , "shvidas" , "rvaas" , "cxraas")
fun writeWithWords(num : Int) : String {
    if(num == 0) return "nuli"

    return when {
        num < 20 -> underTwenty[num]
        num < 30 -> wholeNums[num / 10 + 1] + if(num % 10 != 0) "da" + underTwenty[num % 10] else "i"
        num < 100 -> {
            wholeNums[num / 10] + if(num % 20 != 0) "da" + underTwenty[num % 20] else "i"
        }
        num < 1000 -> {
            val tmp1 = num % 100
            val tmp2 = tmp1 % 20
            hundreds[num / 100] +  if(num % 100 != 0)  writeWithWords(num % 100) else "i"
        }
        num == 1000 -> "atasi"
        else -> "Please input only number from 0 to 1000"
    }


}


