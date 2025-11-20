package com.example.homework_20.presentation.common.screen.register

import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.homework_20.R
import com.example.homework_20.databinding.FragmentRegisterBinding
import com.example.homework_20.presentation.common.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val viewModel: RegisterViewModel by viewModels()
    override fun bind() {
        setupUI()
        observeEffects()
        observeState()
    }

    override fun listeners() {}


    private fun setupUI() = with(binding) {
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
            viewModel.processIntent(
                RegisterIntent.Register(
                    email = etEmail.text.toString(),
                    password = etPassword.text.toString(),
                    repeatPassword = etRepeatPassword.text.toString()
                )
            )
        }
    }

    private fun observeState() = with(binding) {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                btRegister.isEnabled = state.isRegisterEnabled
            }
        }
    }

    private fun observeEffects() {
        lifecycleScope.launch {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is RegisterEffect.ShowToast -> {
                        Toast.makeText(requireContext(), effect.message, Toast.LENGTH_SHORT).show()
                    }

                    is RegisterEffect.NavigateToLoginWithData -> {

                        parentFragmentManager.setFragmentResult(
                            "register_result",
                            bundleOf(
                                "email" to effect.email,
                                "password" to effect.password
                            )
                        )
                        findNavController().navigate(R.id.action_registerFragment_to_logInFragment)
                    }
                }
            }
        }
    }
}