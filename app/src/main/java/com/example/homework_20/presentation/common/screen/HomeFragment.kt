package com.example.homework_20.presentation.common.screen

import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework_20.R
import com.example.homework_20.data.network.RetrofitInstance
import com.example.homework_20.databinding.FragmentHomeBinding
import com.example.homework_20.presentation.common.common.BaseFragment
import com.example.homework_20.presentation.common.adapter.UsersAdapter
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val adapter = UsersAdapter()

    override fun bind() {
        setUp()
        goToProfile()
    }

    override fun listeners() {

    }

    private fun setUp() = with(binding) {
        rvUsers.layoutManager = LinearLayoutManager(requireContext())
        rvUsers.adapter = adapter
        loadUsers()
    }

    private fun loadUsers() {
        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.api.getUsers()
                if (response.isSuccessful) {
                    adapter.submitList(response.body()?.data)
                } else {
                    Toast.makeText(requireContext(), "Error loading users", Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToProfile() {
        binding.btProfile.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }

    }
}