package com.example.homework_13.address

data class Address(
    val id : Long = System.currentTimeMillis() ,
    val type : String ,
    val address : String ,
    val isSelected : Boolean = false
)