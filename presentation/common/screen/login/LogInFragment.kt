package com.example.homework_20.presentation.common.screen.login

import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.homework_20.R
import com.example.homework_20.databinding.FragmentLogInBinding
import com.example.homework_20.presentation.common.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LogInFragment : BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()


    override fun bind() {
        setupFragmentResultListener()
        setupUI()
        observeEffects()
        observeState()
    }

    override fun listeners() {}


    private fun setupUI() = with(binding) {
        etEmail.addTextChangedListener {
            viewModel.processIntent(LoginIntent.EmailChanged(it.toString()))
        }
        etPassword.addTextChangedListener {
            viewModel.processIntent(LoginIntent.PasswordChanged(it.toString()))
        }
        btLogin.setOnClickListener {
            viewModel.processIntent(
                LoginIntent.Login(
                    email = etEmail.text.toString(),
                    password = etPassword.text.toString(),
                    rememberMe = rbRemember.isChecked
                )
            )
        }

    }

    private fun observeEffects() {
        lifecycleScope.launch {
            viewModel.effect.collectLatest { effect ->
                when (effect) {
                    is LoginEffect.NavigateToHome -> {
                        parentFragmentManager.setFragmentResult(
                            "login_result",
                            bundleOf("email" to viewModel.state.value.email)
                        )
                        findNavController().navigate(R.id.action_logInFragment_to_homeFragment)
                    }

                    is LoginEffect.ShowToast -> {
                        Toast.makeText(requireContext(), effect.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setupFragmentResultListener() {
        parentFragmentManager.setFragmentResultListener(
            "register_result",
            viewLifecycleOwner
        ) { _, bundle ->
            val email = bundle.getString("email", "")
            val password = bundle.getString("password", "")
            viewModel.processIntent(LoginIntent.FillAfterRegistration(email, password))
        }
    }

    private fun observeState() = with(binding) {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                if (etEmail.text.toString() != state.email) {
                    etEmail.setText(state.email)
                }

                if (etPassword.text.toString() != state.password) {
                    etPassword.setText(state.password)
                }

                btLogin.isEnabled = state.isLoginEnabled
            }
        }
    }

}