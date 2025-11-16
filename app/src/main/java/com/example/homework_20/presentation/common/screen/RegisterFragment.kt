package com.example.homework_20.presentation.common.screen

import android.util.Patterns
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.homework_20.R
import com.example.homework_20.data.dto.LoginRequestDto
import com.example.homework_20.data.network.RetrofitInstance
import com.example.homework_20.databinding.FragmentRegisterBinding
import com.example.homework_20.presentation.common.common.BaseFragment
import kotlinx.coroutines.launch


class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    override fun bind() {
        setUp()
    }

    override fun listeners() {}

    private fun setUp() = with(binding) {
        btRegister.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val repeatPassword = etRepeatPassword.text.toString()

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(requireContext(), "Invalid email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != repeatPassword) {
                Toast.makeText(requireContext(), "Passwords don't match", Toast.LENGTH_SHORT).show()
            }

            registerUser(email, password)
        }
    }

    private fun registerUser(email: String, password: String) {
        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.api.register(LoginRequestDto(email, password))
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Registered successfully", Toast.LENGTH_SHORT)
                        .show()

                    parentFragmentManager.setFragmentResult(
                        "register_result",
                        bundleOf(
                            "email" to email,
                            "password" to password
                        )
                    )


                    findNavController().navigate(R.id.action_registerFragment_to_logInFragment)
                } else {
                    Toast.makeText(requireContext(), "Registration failed!", Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}