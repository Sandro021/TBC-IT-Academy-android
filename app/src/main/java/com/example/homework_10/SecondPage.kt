package com.example.homework_10

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.homework_10.databinding.FragmentSecondPageBinding
import com.google.android.material.snackbar.Snackbar


class SecondPage : Fragment() {
    private var _binding: FragmentSecondPageBinding? = null
    private val binding get() = _binding!!

    private lateinit var name: EditText
    private lateinit var lastName: EditText
    private lateinit var age: EditText
    private lateinit var mail: EditText

    private lateinit var addUserButton: Button
    private lateinit var updateButton: Button

    private lateinit var removeButton: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }

    private fun setUp() {
        initialize()
        createUser()
        chooseMode()
        removeUser()
    }

    private fun initialize() = with(binding) {
        name = etFirstName
        lastName = etLastName
        mail = etEmail
        age = etAge
        addUserButton = btAddUsers
        updateButton = btUpdateUser
        removeButton = btRemoveUser
    }

    private fun createUser() {
        updateButton.visibility = View.GONE
        removeButton.visibility = View.GONE

        addUserButton.setOnClickListener {
            val firstNameText = name.text.toString()
            val lastNameText = lastName.text.toString()
            val ageToInt = age.text.toString()
            val mailText = mail.text.toString()

            if (!isValidEmail(mailText)) {
                Snackbar.make(
                    binding.root,
                    R.string.please_enter_valid_email_address,
                    Snackbar.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (firstNameText.isEmpty() || lastNameText.isEmpty() || ageToInt.isEmpty() || mailText.isEmpty()) {
                emptyFieldsErrorText()
                return@setOnClickListener
            }
            if (UsersStorage.users.containsKey(mailText)) {
                Snackbar.make(binding.root, R.string.user_already_exists, Snackbar.LENGTH_SHORT)
                    .show()
                clearFields()
                return@setOnClickListener
            } else {
                val user = Userinfo(firstNameText, lastNameText, ageToInt.toInt())
                UsersStorage.users.put(mailText, user)
                Snackbar.make(binding.root, R.string.user_added_successfully, Snackbar.LENGTH_SHORT)
                    .show()
                clearFields()
                UsersStorage.added = true
                UsersStorage.deleted = false
                UsersStorage.updated = false
                findNavController().navigate(R.id.action_secondPage_to_firstPage)
            }
        }
    }

    private fun chooseMode() {
        when (UsersStorage.mode) {
            "add" -> createUser()
            "update" -> updateUser()
        }
    }

    private fun updateUser() {
        updateButton.visibility = View.VISIBLE
        removeButton.visibility = View.VISIBLE
        if (UsersStorage.users.isEmpty()) {
            Snackbar.make(binding.root, R.string.users_do_not_exist, Snackbar.LENGTH_SHORT).show()
        }
        val randomKey = UsersStorage.users.keys.random()

        val randomUser = UsersStorage.users[randomKey]

        addUserButton.visibility = View.GONE

        name.setText(randomUser?.firstName)
        lastName.setText(randomUser?.lastName)
        age.setText(randomUser?.age.toString())
        mail.setText(randomKey)
        mail.isEnabled = false

        updateButton.setOnClickListener {
            if (name.text.isEmpty() || lastName.text.isEmpty() || age.text.isEmpty()) {
                emptyFieldsErrorText()
                return@setOnClickListener
            } else {
                UsersStorage.users[randomKey]?.firstName = name.text.toString()
                UsersStorage.users[randomKey]?.lastName = lastName.text.toString()
                UsersStorage.users[randomKey]?.age = age.text.toString().toInt()
                clearFields()
                UsersStorage.updated = true
                UsersStorage.added = false
                UsersStorage.deleted = false
                findNavController().navigate(R.id.action_secondPage_to_firstPage)
            }
        }
    }

    private fun removeUser() {
        removeButton.setOnClickListener {
            val mailText = mail.text.toString()
            UsersStorage.users.remove(mailText)
            UsersStorage.removedCount++
            UsersStorage.deleted = true
            UsersStorage.added = false
            UsersStorage.updated = false
            clearFields()
            findNavController().navigate(R.id.action_secondPage_to_firstPage)
        }
    }

    private fun isValidEmail(mailAddress: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(mailAddress).matches()
    }


    private fun emptyFieldsErrorText() {
        Snackbar.make(binding.root, R.string.fillAllFields, Snackbar.LENGTH_SHORT).show()
    }

    private fun clearFields() {
        name.text.clear()
        lastName.text.clear()
        age.text.clear()
        mail.text.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}