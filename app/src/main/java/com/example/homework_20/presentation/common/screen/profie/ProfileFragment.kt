package com.example.homework_20.presentation.common.screen.profie

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.homework_20.R
import com.example.homework_20.databinding.FragmentProfileBinding
import com.example.homework_20.presentation.common.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by viewModels()


    override fun bind() {
        setupFragmentResultListener()
        observeState()

    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->

                Log.d("TEST", "State email: ${state.email}")
                binding.tvEmail.text = state.email?.ifEmpty { "No email" }

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
            val passedEmail = bundle.getString("email").orEmpty()
            binding.tvEmail.text = passedEmail
            viewModel.processIntent(ProfileIntent.LoadProfile(passedEmail))
        }
    }

    override fun listeners() {
        binding.btLogOut.setOnClickListener {
            viewModel.processIntent(ProfileIntent.Logout)
        }
    }

}