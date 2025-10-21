package com.example.homework_11

import androidx.lifecycle.ViewModel

class  SharedViewModel : ViewModel() {
    val users = mutableMapOf<String , Userinfo>()
    var removedCount = 0
    var added = false
    var deleted = false
    var updated = false
    var mode = ""

    var selectedUserMail : String? = null
}