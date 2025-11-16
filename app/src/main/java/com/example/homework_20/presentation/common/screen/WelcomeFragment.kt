package com.example.homework_20.presentation.common.screen

import android.content.Context
import androidx.navigation.fragment.findNavController
import com.example.homework_20.R
import com.example.homework_20.databinding.FragmentWelcomeBinding
import com.example.homework_20.presentation.common.common.BaseFragment


class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(FragmentWelcomeBinding::inflate) {
    override fun bind() {
        goToSavedSession()
    }

    override fun listeners() = with(binding) {
        btLogin.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_logInFragment)
        }
        btRegister.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_registerFragment)
        }
    }

    private fun goToSavedSession() {
        if (isLoggedIn()) {
            findNavController().navigate(R.id.action_welcomeFragment_to_homeFragment)
            return
        }
    }

    private fun isLoggedIn(): Boolean {
        val prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return prefs.getString("email", null) != null
    }
}