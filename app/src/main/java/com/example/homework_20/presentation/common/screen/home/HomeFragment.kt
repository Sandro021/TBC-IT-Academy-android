package com.example.homework_20.presentation.common.screen.home

import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework_20.R
import com.example.homework_20.databinding.FragmentHomeBinding
import com.example.homework_20.presentation.common.adapter.UsersAdapter
import com.example.homework_20.presentation.common.common.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val adapter = UsersAdapter()

    private var loggedInEmail: String? = null
    private val viewModel: HomeViewModel by viewModels()
    override fun bind() {
        setupFragmentResultListener()
        setupUI()
        observeState()
        observeEffects()
        viewModel.processIntent(HomeIntent.LoadUsers)
    }

    override fun listeners() {
        binding.btProfile.setOnClickListener {
            parentFragmentManager.setFragmentResult(
                "profile_data",
                bundleOf("email" to loggedInEmail)

            )
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
    }

    private fun setupUI() = with(binding) {

        rvUsers.layoutManager = LinearLayoutManager(requireContext())
        rvUsers.adapter = adapter


        btProfile.setOnClickListener {
            viewModel.processIntent(HomeIntent.GoToProfile)
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                adapter.submitList(state.users)

                state.errorMessage?.let { message ->
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun observeEffects() {
        lifecycleScope.launch {
            viewModel.effect.collectLatest { effect ->
                when (effect) {
                    is HomeEffect.ShowToast -> {
                        Toast.makeText(requireContext(), effect.message, Toast.LENGTH_SHORT).show()
                    }

                    HomeEffect.NavigateToProfile -> {
                        findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
                    }
                }
            }
        }
    }

    private fun setupFragmentResultListener() {
        parentFragmentManager.setFragmentResultListener(
            "login_result",
            viewLifecycleOwner
        ) { _, bundle ->
            loggedInEmail = bundle.getString("email")

        }
    }
}