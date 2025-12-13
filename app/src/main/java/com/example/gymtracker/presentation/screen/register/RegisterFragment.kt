package com.example.gymtracker.presentation.screen.register

import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.gymtracker.R
import com.example.gymtracker.databinding.FragmentRegisterBinding
import com.example.gymtracker.presentation.screen.register.RegisterIntent
import com.example.gymtracker.presentation.screen.register.RegisterState
import com.example.gymtracker.presentation.screen.register.RegisterViewModel
import com.example.gymtracker.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val viewModel: RegisterViewModel by viewModels()
    override fun bind() {

    }

    override fun listeners() {
        setUpListeners()
    }

    private fun setUpListeners() = with(binding) {
        etEmail.addTextChangedListener {
            viewModel.processIntent(RegisterIntent.EmailChanged(it.toString()))
        }
        etPassword.addTextChangedListener {
            viewModel.processIntent(RegisterIntent.PasswordChanged(it.toString()))
        }
        etRepeatPassword.addTextChangedListener {
            viewModel.processIntent(RegisterIntent.RepeatPasswordChanged(it.toString()))
        }
        btRegister.setOnClickListener {
            viewModel.processIntent(RegisterIntent.ClickRegister)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    render(state)
                }
            }
        }
    }

    private fun render(state: RegisterState) {
        binding.btRegister.isEnabled = !state.isLoading

        state.errorMessage?.let {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()

        }

        if (state.navigateToHome) {
            findNavController().navigate(R.id.action_registerFragment_to_userFragment)
            viewModel.processIntent(RegisterIntent.NavigationHandled)
        }
    }

}