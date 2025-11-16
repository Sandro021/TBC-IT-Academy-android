package com.example.homework_20.presentation.common.screen

import android.content.Context
import androidx.core.content.edit
import androidx.navigation.fragment.findNavController
import com.example.homework_20.R
import com.example.homework_20.databinding.FragmentProfileBinding
import com.example.homework_20.presentation.common.common.BaseFragment


class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    override fun bind() {
        setUp()
        goToWelcome()
    }

    override fun listeners() {}

    private fun setUp() {
        binding.tvEmail.text = getSavedEmail()
    }

    private fun getSavedEmail(): String {
        val prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return prefs.getString("email", "Unknown user") ?: "Unknown user"

    }

    private fun goToWelcome() {
        binding.btLogOut.setOnClickListener {
            clearSession()
            findNavController().navigate(R.id.action_profileFragment_to_logInFragment)
        }
    }

    private fun clearSession() {
        val prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        prefs.edit { clear() }
    }
}