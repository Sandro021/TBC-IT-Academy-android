package com.example.homework_20.presentation.common.screen.profie

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.homework_20.R
import com.example.homework_20.databinding.FragmentProfileBinding
import com.example.homework_20.presentation.common.common.BaseFragment
import com.example.homework_20.presentation.common.screen.sessionRepository.SessionRepository
import kotlinx.coroutines.launch

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory(SessionRepository(requireContext()))
    }

    override fun bind() {
        setupFragmentResultListener()
        observeState()

    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                if (state.isLoggedOut) {
                    findNavController().navigate(R.id.action_profileFragment_to_logInFragment)
                }
            }
        }
    }

    private fun setupFragmentResultListener() {
        parentFragmentManager.setFragmentResultListener(
            "profile_data",
            viewLifecycleOwner
        ) { _, bundle ->
            val email = bundle.getString("email").orEmpty()
            binding.tvEmail.text = email
            viewModel.processIntent(ProfileIntent.LoadProfile(email))
        }
    }

    override fun listeners() {
        binding.btLogOut.setOnClickListener {
            viewModel.processIntent(ProfileIntent.Logout)
        }
    }


}