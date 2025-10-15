package com.example.exam_2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.exam_2.databinding.AddUserBinding
import com.google.android.material.snackbar.Snackbar

class AdduserActivity : ComponentActivity() {
    private lateinit var binding: AddUserBinding

    private lateinit var firstName: EditText

    private lateinit var lastName: EditText
    private lateinit var birthday: EditText
    private lateinit var email: EditText
    private lateinit var address: EditText
    private lateinit var addUserButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = AddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()
    }

    fun setup() {
        initialize()
        addUserToStorage()
    }

    fun initialize() = with(binding) {
        firstName = etFirstName
        lastName = etLastName
        address = etAddress
        email = etEmail
        birthday = etBirthday
        addUserButton = btAddUser
    }

    fun addUserToStorage() {
        addUserButton.setOnClickListener {
            val mailText = email.text.toString()
            if(!isValidEmail(mailText)) {
                Snackbar.make(binding.root ,
                    getString(R.string.please_enter_valid_email_address) , Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (firstName.text.isEmpty() || lastName.text.isEmpty() || email.text.isEmpty()
                || birthday.text.isEmpty()
            ) {
                Snackbar.make(
                    binding.root,
                    getString(R.string.please_fill_all_the_fields), Snackbar.LENGTH_SHORT
                ).show()
            } else {
                val id = UserStorage.users.size + 1
                val tmpUser = Userinfo(
                    id,
                    firstName.text.toString(),
                    lastName.text.toString(),
                    address.text.toString(),
                    email.text.toString(),
                    birthday = birthday.text.toString()
                )
                UserStorage.users.add(tmpUser)
                goBack()
                Log.d("user", UserStorage.users.toString())
            }
        }
    }

    fun goBack() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    fun isValidEmail(mailAddress: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(mailAddress).matches()
    }
}