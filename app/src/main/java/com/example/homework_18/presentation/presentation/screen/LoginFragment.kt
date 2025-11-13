package com.example.homework_18.presentation.presentation.screen

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.homework_18.presentation.presentation.common.BaseFragment
import com.example.homework_18.databinding.FragmentLoginBinding
import com.example.homework_18.presentation.presentation.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: AuthViewModel by activityViewModels()

    override fun bind() {
        setUp()
    }

    override fun listeners() {

    }

    private fun setUp() = with(binding) {


        btLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            viewModel.login(email, password)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.status.collect { message ->
                    if (message.isNotEmpty()) {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }

}