package com.example.gymtracker.presentation.screen.welcome

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.gymtracker.R
import com.example.gymtracker.databinding.FragmentWelcomeBinding
import com.example.gymtracker.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(FragmentWelcomeBinding::inflate) {
    private val viewModel: WelcomeViewModel by viewModels()

    override fun bind() {

    }

    override fun listeners() {
        binding.btRegister.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_registerFragment)
        }
        binding.btLogin.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment2)
        }
        viewModel.checkSession()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { navigate ->
                    if (navigate) {
                        findNavController().navigate(R.id.action_welcomeFragment_to_homeFragment2)
                        viewModel.navigationHandled()
                    }
                }
            }
        }
    }

}