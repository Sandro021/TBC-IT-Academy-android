package com.example.homework_24.presentation.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.homework_24.R
import com.example.homework_24.databinding.FragmentWelcomeBinding
import com.example.homework_24.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(FragmentWelcomeBinding::inflate) {
    override fun bind() {

    }

    override fun listeners() {
        binding.btStart.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_userFragment)
        }
    }

}