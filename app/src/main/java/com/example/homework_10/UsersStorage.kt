package com.example.homework_10

object UsersStorage {
    val users = mutableMapOf<String , Userinfo>()
    var removedCount = 0

    var added = false
    var deleted = false
    var updated = false

    var mode = ""
}