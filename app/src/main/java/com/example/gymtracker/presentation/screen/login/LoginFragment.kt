package com.example.gymtracker.presentation.screen.login

import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.gymtracker.R
import com.example.gymtracker.databinding.FragmentLoginBinding
import com.example.gymtracker.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun bind() {

    }

    override fun listeners() {
        setUpListeners()
    }

    private fun setUpListeners() = with(binding) {
        etEmail.addTextChangedListener {
            viewModel.processIntent(LoginIntent.EmailChanged(it.toString()))
        }
        etPassword.addTextChangedListener {
            viewModel.processIntent(LoginIntent.PasswordChanged(it.toString()))
        }

        btLogin.setOnClickListener {
            viewModel.processIntent(LoginIntent.ClickLogin)
        }
        rbRemember.setOnCheckedChangeListener { _, isChecked ->
            viewModel.processIntent(LoginIntent.RememberMeChanged(isChecked))
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    render(state)
                }
            }
        }
    }

    private fun render(state: LoginState) {
        binding.btLogin.isEnabled = !state.isLoading

        state.errorMessage?.let {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        if (state.navigateToHome) {
            findNavController().navigate(R.id.action_loginFragment2_to_homeFragment2)
            viewModel.processIntent(LoginIntent.NavigationHandled)
        }
    }

}