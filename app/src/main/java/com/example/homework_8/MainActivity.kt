package com.example.homework_8

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.homework_8.databinding.ActivityMainBinding
import android.graphics.Color
import android.view.View


class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var age: EditText
    private lateinit var mail: EditText
    private lateinit var activeMembers: TextView
    private lateinit var removedMembers: TextView
    private lateinit var addUserButton: Button
    private lateinit var findEmail: EditText
    private lateinit var deleteButton: Button
    private lateinit var updateUserButton: Button
    private lateinit var status: TextView
    private val users = mutableMapOf<String, Userinfo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialize()
        addUsers()
        deleteUser()
        updateUser()

    }


    fun initialize() {
        with(binding) {
            firstName = etFirstName
            lastName = etLastName
            age = etAge
            mail = etEmail
            activeMembers = tvActiveMembers
            removedMembers = tvDeletedMembers
            addUserButton = btAddUsers
            findEmail = etFindUser
            deleteButton = btRemoveUser
            updateUserButton = btUpdateUser
            status = tvStatus
        }
    }

    @SuppressLint("SetTextI18n")
    fun addUsers() {
        addUserButton.setOnClickListener {
            val firstNameText = firstName.text.toString()
            val lastNameText = lastName.text.toString()
            val ageToInt = age.text.toString()
            val mailText = mail.text.toString()

            if (!isValidEmail(mailText)) {
                errorMessage()
                Toast.makeText(
                    this,
                    getString(R.string.please_enter_valid_email_address), Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener

            }

            if (firstNameText.isEmpty() || lastNameText.isEmpty() || ageToInt.isEmpty() || mailText.isEmpty()) {
                errorMessage()
                Toast.makeText(this, R.string.fillAllFields, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val user = Userinfo(firstNameText, lastNameText, ageToInt.toInt())

            if (users.containsKey(mailText)) {
                errorMessage()
                Toast.makeText(this, R.string.user_already_exists, Toast.LENGTH_SHORT).show()
                clearFields()
            } else {
                users[mailText] = user
                Toast.makeText(
                    this,
                    getString(R.string.user_added_successfully), Toast.LENGTH_SHORT
                ).show()
                clearFields()
                val count = users.size
                activeMembers.text = getString(R.string.active_members, count)
                status.text = getString(R.string.registration_successful)
                status.setTextColor(Color.GREEN)
                status.visibility = View.VISIBLE
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun deleteUser() {
        var removed = 0
        deleteButton.setOnClickListener {
            val emailToDelete = findEmail.text.toString()
            if (emailToDelete.isEmpty()) {
                errorMessage()
                Toast.makeText(
                    this,
                    getString(R.string.enter_email_to_delete_user),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (!users.containsKey(emailToDelete)) {
                errorMessage()
                Toast.makeText(this, getString(R.string.User_does_not_exists), Toast.LENGTH_SHORT)
                    .show()
            } else {
                users.remove(emailToDelete)
                val count = users.size
                removed++
                removedMembers.text = getString(R.string.removed_users, removed)
                Toast.makeText(this, R.string.deleted_members, Toast.LENGTH_SHORT).show()
                activeMembers.text = getString(R.string.active_members, count)
                status.text = getString(R.string.user_deleted_successfully)
                status.setTextColor(Color.GREEN)
                status.visibility = View.VISIBLE
            }
        }

    }

    @SuppressLint("SetTextI18n")
    fun updateUser() {
        updateUserButton.setOnClickListener {
            val nameText = firstName.text.toString()
            val lastNameText = lastName.text.toString()
            val ageText = age.text.toString()
            val mailText = mail.text.toString()

            if (mailText.isEmpty()) {
                errorMessage()
                Toast.makeText(
                    this,
                    getString(R.string.fill_email_field_to_update_user), Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (!users.containsKey(mailText)) {
                errorMessage()
                Toast.makeText(
                    this,
                    getString(R.string.first_you_have_to_create_account), Toast.LENGTH_SHORT
                ).show()
            } else {
                if (!nameText.isEmpty()) {
                    users[mailText]?.firstName = nameText
                }
                if (!lastNameText.isEmpty()) {
                    users[mailText]?.lastName = lastNameText
                }
                if (!ageText.isEmpty()) {
                    users[mailText]?.age = ageText.toInt()

                }
                Toast.makeText(
                    this,
                    getString(R.string.user_updated_successfully), Toast.LENGTH_SHORT
                ).show()
                clearFields()
                status.text = getString(R.string.update_successfully)
                status.setTextColor(Color.GREEN)
                status.visibility = View.VISIBLE
            }

        }
    }

    fun clearFields() {
        firstName.text.clear()
        lastName.text.clear()
        age.text.clear()
        mail.text.clear()
    }

    fun isValidEmail(mailAddress: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(mailAddress).matches()
    }

    @SuppressLint("SetTextI18n")
    fun errorMessage() {
        status.text = getString(R.string.error)
        status.setTextColor(Color.RED)
        status.visibility = View.VISIBLE
    }
}

