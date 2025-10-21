package com.example.homework_11

import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.homework_11.databinding.FragmentAddUserPageBinding

import com.google.android.material.snackbar.Snackbar


class AddUserPage : BaseFragment<FragmentAddUserPageBinding>(FragmentAddUserPageBinding::inflate) {
    private lateinit var name: EditText
    private lateinit var lastName: EditText
    private lateinit var age: EditText
    private lateinit var mail: EditText

    private lateinit var addUserButton: Button
    private lateinit var updateButton: Button

    private lateinit var removeButton: Button

    private lateinit var adapter : RecyclerAdapter
    private val viewModel: SharedViewModel by activityViewModels()

    override fun bind() {
        setUp()
    }

    private fun setUp() {
        initialize()
        createUser()
        chooseMode()
        removeUser()
        adapter = RecyclerAdapter(viewModel.users)
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
            if (viewModel.users.containsKey(mailText)) {
                Snackbar.make(binding.root, R.string.user_already_exists, Snackbar.LENGTH_SHORT)
                    .show()
                clearFields()
                return@setOnClickListener
            } else {
                val user = Userinfo(firstNameText, lastNameText, ageToInt.toInt())
                viewModel.users.put(mailText, user)
                Snackbar.make(binding.root, R.string.user_added_successfully, Snackbar.LENGTH_SHORT)
                    .show()
                clearFields()
                viewModel.added = true
                viewModel.deleted = false
                viewModel.updated = false
                adapter.addUser(mailText , user)
                findNavController().navigate(R.id.action_addUserPage_to_displayUsersPage)

            }
        }
    }

    private fun chooseMode() {
        when (viewModel.mode) {
            "add" -> createUser()
            "update" -> updateUser()
        }
    }

    private fun updateUser() {
        updateButton.visibility = View.VISIBLE
        removeButton.visibility = View.VISIBLE
        if (viewModel.users.isEmpty()) {
            Snackbar.make(binding.root, R.string.users_do_not_exist, Snackbar.LENGTH_SHORT).show()
        }
        val randomKey = viewModel.users.keys.random()

        val randomUser = viewModel.users[randomKey]

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
                viewModel.users[randomKey]?.firstName = name.text.toString()
                viewModel.users[randomKey]?.lastName = lastName.text.toString()
                viewModel.users[randomKey]?.age = age.text.toString().toInt()
                clearFields()
                viewModel.updated = true
                viewModel.added = false
                viewModel.deleted = false
                findNavController().navigate(R.id.action_addUserPage_to_displayUsersPage)
            }
        }
    }

    private fun removeUser() {
        removeButton.setOnClickListener {
            val mailText = mail.text.toString()
            viewModel.users.remove(mailText)
            viewModel.removedCount++
            viewModel.deleted = true
            viewModel.added = false
            viewModel.updated = false
            clearFields()
            findNavController().navigate(R.id.action_addUserPage_to_displayUsersPage)
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

}