package com.example.homework_18.presentation.presentation.screen

import androidx.navigation.fragment.findNavController
import com.example.homework_18.R
import com.example.homework_18.presentation.presentation.common.BaseFragment
import com.example.homework_18.databinding.FragmentWelcomeBinding

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(FragmentWelcomeBinding::inflate) {
    override fun bind() {

    }

    override fun listeners() {
        goToRegister()
        goToLogin()
    }

    private fun goToRegister() {
        binding.btRegister.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_registerFragment)
        }
    }

    private fun goToLogin() {
        binding.btLogin.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment)
        }
    }
}