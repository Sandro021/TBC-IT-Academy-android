package com.example.exam_2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.widget.addTextChangedListener
import com.example.exam_2.databinding.MainPaigeBinding
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : ComponentActivity() {
    private lateinit var binding: MainPaigeBinding

    private lateinit var addUserButton: Button
    private lateinit var searchField: EditText
    private lateinit var infoField: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = MainPaigeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUp()

    }

    fun searchUser() {
        searchField.addTextChangedListener { text ->
            val infoText = text.toString()
            findUser(infoText)
        }
    }

    fun setUp() {
        initialize()
        addUser()
        searchUser()
    }

    fun initialize() = with(binding) {
        addUserButton = btAddUser
        searchField = etSearchField
        infoField = etInfoField
    }

    fun addUser() {
        addUserButton.setOnClickListener {
            val intent = Intent(this, AdduserActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    fun findUser(text: String) {


        val matches = UserStorage.users.filter { user ->
            user.firstName.contains(text, ignoreCase = true) ||
                    user.lastName.contains(text, ignoreCase = true) ||
                    user.birthday.contains(text, ignoreCase = true)
        }
        val lowestId = matches.minByOrNull { it.id }

        if (lowestId != null) {
           // convertToValidDate(lowestId.birthday)
            infoField.setText(
                """
                ID: ${lowestId.id}
                Name: ${lowestId.firstName} ${lowestId.lastName}
                Birthday: ${lowestId.birthday} 
                Address: ${lowestId.address}
                Email: ${lowestId.email}
            """
            )
        }
    }

    fun convertToValidDate(str: String): String {
        if (str.length > 8) {
            Snackbar.make(
                binding.root,
                getString(R.string.birthday_cant_be_converted), Snackbar.LENGTH_SHORT
            ).show()
        }
        val day = str.substring(0, 2).toInt()
        val month = str.substring(2, 4).toInt()
        val year = str.substring(4, 8).toInt()
        if (day > 31 || month > 12) {
            Snackbar.make(binding.root, R.string.birthday_cant_be_converted, Snackbar.LENGTH_SHORT)
                .show()
        }
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1 ,day)

        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
        val makeDate = formatter.format(calendar.time)
        return makeDate

        //es ragac ar mushaobs ise rogorc sachiroa da ver movaswari :(
    }
}

