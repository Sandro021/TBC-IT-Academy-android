package com.example.homework_18.screen

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.homework_18.R
import com.example.homework_18.common.BaseFragment
import com.example.homework_18.databinding.FragmentRegisterBinding
import com.example.homework_18.viewmodel.AuthViewModel
import kotlinx.coroutines.launch


class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val viewModel: AuthViewModel by activityViewModels()

    override fun bind() {
        setUp()
    }

    override fun listeners() {

    }

    private fun setUp() = with(binding) {
        btRegister.setOnClickListener {
            val emailInput = etEmail.text.toString()
            val passwordInput = etPassword.text.toString()
            viewModel.register(emailInput, passwordInput)
            findNavController().navigate(R.id.action_registerFragment_to_welcomeFragment)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.status.collect { message ->
                    if (message.isNotEmpty())
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }

        }

    }
}