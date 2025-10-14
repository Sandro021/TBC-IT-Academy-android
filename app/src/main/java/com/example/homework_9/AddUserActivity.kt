package com.example.homework_9

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.homework_9.databinding.AddUserBinding
import com.google.android.material.snackbar.Snackbar
class AddUserActivity : ComponentActivity() {
    private lateinit var binding: AddUserBinding
    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var age: EditText
    private lateinit var mail: EditText

    private lateinit var addUserButton: Button
    private lateinit var updateButton : Button

    private val users = UsersStorage.users
    private lateinit var removeUserButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = AddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUp()
    }

    fun setUp() {
        initialize()
        createUser()
        checkMode()
        removeUser()
    }

    fun initialize() = with(binding) {
        firstName = etFirstName
        lastName = etLastName
        age = etAge
        mail = etEmail
        addUserButton = btAddUsers
        updateButton = btUpdateUser
        removeUserButton = btRemoveUser
    }

    fun createUser() {
        updateButton.visibility = View.GONE
        removeUserButton.visibility = View.GONE
        addUserButton.setOnClickListener {
            val firstNameText = firstName.text.toString()
            val lastNameText = lastName.text.toString()
            val ageToInt = age.text.toString()
            val mailText = mail.text.toString()

            if(!isValidEmail(mailText)) {
                Snackbar.make(binding.root , R.string.please_enter_valid_email_address, Snackbar.LENGTH_SHORT).show()
                clearFields()
                return@setOnClickListener
            }
            if(firstNameText.isEmpty() || lastNameText.isEmpty() || ageToInt.isEmpty() || mailText.isEmpty()) {
                emptyFieldsErrorText()
                return@setOnClickListener
            }
            if(users.containsKey(mailText)) {
                Snackbar.make(binding.root , R.string.user_already_exists , Snackbar.LENGTH_SHORT ).show()
                clearFields()
                return@setOnClickListener
            }else {
                val user = Userinfo(firstNameText,lastNameText,ageToInt.toInt())
                users.put(mailText,user)
                Snackbar.make(binding.root , R.string.user_added_successfully , Snackbar.LENGTH_SHORT).show()
                userAdded()
            }
        }
    }
    fun isValidEmail(mailAddress: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(mailAddress).matches()
    }
    fun emptyFieldsErrorText() {
        Snackbar.make(binding.root , R.string.fillAllFields , Snackbar.LENGTH_SHORT).show()
    }
    fun clearFields() {
        firstName.text.clear()
        lastName.text.clear()
        age.text.clear()
        mail.text.clear()
    }
    fun userAdded() {
        val intent = Intent(this, MainPageActivity::class.java)
        intent.putExtra("status message" , getString(R.string.user_added_successfully))
        setResult(RESULT_OK,intent)
        finish()
    }
    fun updateUserInfo() {
        updateButton.visibility = View.VISIBLE
        removeUserButton.visibility = View.VISIBLE
        if(users.isEmpty()) {
            Snackbar.make(binding.root , R.string.users_do_not_exist , Snackbar.LENGTH_SHORT).show()
            return
        }
        val randomKey = users.keys.random()

        val randomUser = users[randomKey]

        addUserButton.visibility = View.GONE

        firstName.setText(randomUser?.firstName)
        lastName.setText(randomUser?.lastName)
        age.setText(randomUser?.age.toString())
        mail.setText(randomKey)
        mail.isEnabled = false

        updateButton.setOnClickListener {
            if(firstName.text.isEmpty() || lastName.text.isEmpty() || age.text.isEmpty()) {
                emptyFieldsErrorText()
                return@setOnClickListener
            } else {
                users[randomKey]?.firstName = firstName.text.toString()
                users[randomKey]?.lastName = lastName.text.toString()
                users[randomKey]?.age = age.text.toString().toInt()
                Log.d("user updated" , users[randomKey].toString())
                clearFields()
                goBack()
            }
        }
    }
    fun removeUser() {
        removeUserButton.setOnClickListener {
            val mailText = mail.text.toString()
            users.remove(mailText)
            UsersStorage.removedCount++
            clearFields()
            val intent = Intent(this , MainPageActivity::class.java)
            intent.putExtra("status message" , getString(R.string.user_deleted_successfully))
            setResult(RESULT_OK,intent)
            finish()
        }
    }
    fun checkMode() {
        val mode = intent.getStringExtra("mode")
        when(mode) {
            "add" -> createUser()
            "update" -> updateUserInfo()
        }
    }
    fun goBack() {
        val intent = Intent(this , MainPageActivity::class.java)
        intent.putExtra("status message" , getString(R.string.user_updated_successfully))
        setResult(RESULT_OK,intent)
        finish()
    }

}