package com.example.gymtracker.presentation

import androidx.navigation.fragment.findNavController
import com.example.gymtracker.R
import com.example.gymtracker.databinding.FragmentWelcomeBinding
import com.example.gymtracker.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(FragmentWelcomeBinding::inflate) {
    override fun bind() {

    }

    override fun listeners() {
        binding.btRegister.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_registerFragment)
        }
        binding.btLogin.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment2)
        }
    }

}