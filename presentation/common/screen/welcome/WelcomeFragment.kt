package com.example.homework_20.presentation.common.screen.welcome

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.homework_20.R
import com.example.homework_20.databinding.FragmentWelcomeBinding
import com.example.homework_20.presentation.common.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(FragmentWelcomeBinding::inflate) {
    private val viewModel: WelcomeViewModel by viewModels()

    override fun bind() {
        observeEffects()
        viewModel.processIntent(WelcomeIntent.CheckSession)
    }

    override fun listeners() = with(binding) {
        btLogin.setOnClickListener {
            viewModel.processIntent(WelcomeIntent.ClickLogin)
        }
        btRegister.setOnClickListener {
            viewModel.processIntent(WelcomeIntent.ClickRegister)
        }
    }

    private fun observeEffects() {
        lifecycleScope.launch {
            viewModel.effect.collectLatest { effect ->
                when (effect) {
                    WelcomeEffect.NavigateToHome ->
                        findNavController().navigate(R.id.action_welcomeFragment_to_homeFragment)

                    WelcomeEffect.NavigateToLogin ->
                        findNavController().navigate(R.id.action_welcomeFragment_to_logInFragment)

                    WelcomeEffect.NavigateToRegister ->
                        findNavController().navigate(R.id.action_welcomeFragment_to_registerFragment)
                }
            }
        }
    }


}