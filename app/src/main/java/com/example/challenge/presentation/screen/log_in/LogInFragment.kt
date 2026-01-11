package com.example.challenge.presentation.screen.log_in

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.challenge.R
import com.example.challenge.presentation.common.BaseFragment
import com.example.challenge.databinding.FragmentLogInBinding
import com.example.challenge.presentation.extension.showSnackBar
import com.example.challenge.presentation.screen.log_in.contract.LogInState
import com.example.challenge.presentation.screen.log_in.contract.LoginEvent
import com.example.challenge.presentation.screen.log_in.contract.UiEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LogInFragment : BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {

    private val viewModel: LogInViewModel by viewModels()

    override fun bind() {
        bindObserves()
    }

    override fun listeners() {
        bindViewActionListeners()
    }

    fun bindViewActionListeners() {
        binding.btnLogIn.setOnClickListener {
            logIn()
        }
    }

    fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.logInState.collect {
                    handleLogInState(logInState = it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect {
                    handleNavigationEvents(it)
                }
            }
        }
    }

    private fun logIn() {
        viewModel.onEvent(
            LoginEvent.LogIn(
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString()
            )
        )
    }

    private fun handleLogInState(logInState: LogInState) {
        binding.loaderInclude.loaderContainer.visibility =
            if (logInState.isLoading) View.VISIBLE else View.GONE

        logInState.errorMessage?.let {
            binding.root.showSnackBar(message = it)
            viewModel.onEvent(LoginEvent.ResetErrorMessage)
        }
    }

    private fun handleNavigationEvents(event: UiEvent) {
        when (event) {
            UiEvent.NavigateToConnections -> {
                findNavController().navigate(
                    R.id.action_logInFragment_to_connectionsFragment
                )
            }
        }

    }
}
