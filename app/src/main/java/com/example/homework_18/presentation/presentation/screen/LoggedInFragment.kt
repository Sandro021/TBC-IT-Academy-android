package com.example.homework_18.presentation.presentation.screen

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.homework_18.R
import com.example.homework_18.presentation.presentation.common.BaseFragment
import com.example.homework_18.databinding.FragmentLoggedInBinding
import com.example.homework_18.presentation.presentation.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

class LoggedInFragment : BaseFragment<FragmentLoggedInBinding>(FragmentLoggedInBinding::inflate) {

    private val viewModel: AuthViewModel by activityViewModels()
    override fun bind() {
        setUpUI()
    }

    override fun listeners() {
        logOut()
        deleteUser()
        setUpUI()
    }

    private fun logOut() {
        binding.btLogOut.setOnClickListener {
            viewModel.logOut()
            findNavController().navigate(R.id.action_loggedInFragment_to_welcomeFragment)
        }
    }

    private fun deleteUser() {
        binding.btDelete.setOnClickListener {
            viewModel.deleteUser()
            findNavController().navigate(R.id.action_loggedInFragment_to_welcomeFragment)
        }
    }

    private fun setUpUI() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.username.collect { name ->
                    tvUser.text = name
                }
            }
        }
    }
}