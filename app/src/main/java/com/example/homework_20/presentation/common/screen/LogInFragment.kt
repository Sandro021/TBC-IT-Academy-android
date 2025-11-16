package com.example.homework_20.presentation.common.screen

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.homework_20.R
import com.example.homework_20.data.dto.LoginRequestDto
import com.example.homework_20.data.network.RetrofitInstance
import com.example.homework_20.databinding.FragmentLogInBinding
import com.example.homework_20.presentation.common.common.BaseFragment
import kotlinx.coroutines.launch


class LogInFragment : BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {
    override fun bind() {
        setUp()
        afterRegistration()
    }

    override fun listeners() {}


    private fun setUp() = with(binding) {
        etEmail.addTextChangedListener { validateInputs() }
        etPassword.addTextChangedListener { validateInputs() }
        btLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val rememberMe = rbRemember.isChecked

            login(email, password, rememberMe)
        }
    }

    private fun afterRegistration() = with(binding) {
        parentFragmentManager.setFragmentResultListener(
            "register_result",
            viewLifecycleOwner
        ) { _, bundle ->
            val email = bundle.getString("email", "")
            val password = bundle.getString("password", "")

            etEmail.setText(email)
            etPassword.setText(password)
        }
    }


    private fun login(email: String, password: String, rememberMe: Boolean) {
        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.api.login(LoginRequestDto(email, password))
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Logged in ", Toast.LENGTH_SHORT).show()
                    if (rememberMe) {
                        saveSession(email)
                    }
                    val sharedPref = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                    sharedPref.edit { putString("email", email) }
                    findNavController().navigate(R.id.action_logInFragment_to_homeFragment)
                } else {
                    Toast.makeText(requireContext(), "Login failed", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun saveSession(email: String) {
        val sharedPref = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        sharedPref.edit { putString("email", email) }
    }

    private fun validateInputs() = with(binding) {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        val isValidEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordNotEmpty = password.isNotEmpty()

        btLogin.isEnabled = isValidEmail && isPasswordNotEmpty
    }
}