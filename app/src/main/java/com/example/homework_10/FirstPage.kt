package com.example.homework_10

import android.os.Bundle
import android.graphics.Color
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.homework_10.databinding.FragmentFirstPageBinding
import com.google.android.material.snackbar.Snackbar


class FirstPage : Fragment() {


    private var _binding: FragmentFirstPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }

    private fun setUp() {
        addNewUser()
        updateActiveUserCount()
        updateRemovedUsers()
        updateStatusMessage()
        updateUser()
    }

    private fun addNewUser() {
        binding.btAddUsers.setOnClickListener {
            UsersStorage.mode = getString(R.string.add)
            findNavController().navigate(R.id.action_firstPage_to_secondPage)
        }
    }

    private fun updateActiveUserCount() {
        binding.tvActiveMembers.text = getString(R.string.active_members, UsersStorage.users.size)
    }

    private fun updateRemovedUsers() {
        binding.tvDeletedMembers.text = getString(R.string.removed_users, UsersStorage.removedCount)
    }


    private fun updateStatusMessage() = with(binding) {
        if (UsersStorage.added) {
            tvStatus.text = getString(R.string.user_added_successfully)
            tvStatus.setTextColor(Color.GREEN)
            tvStatus.visibility = View.VISIBLE
            UsersStorage.added = false
        }
        if (UsersStorage.updated) {
            tvStatus.text = getString(R.string.user_updated_successfully)
            tvStatus.setTextColor(Color.GREEN)
            tvStatus.visibility = View.VISIBLE
            UsersStorage.updated = false
        }
        if (UsersStorage.deleted) {
            tvStatus.text = getString(R.string.user_deleted_successfully)
            tvStatus.setTextColor(Color.GREEN)
            tvStatus.visibility = View.VISIBLE
            UsersStorage.updated = false
        }
    }

    private fun updateUser() {
        binding.btUpdateUser.setOnClickListener {
            if (UsersStorage.users.isEmpty()) {
                Snackbar.make(binding.root, R.string.users_do_not_exist, Snackbar.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            } else {
                UsersStorage.mode = getString(R.string.update)
                findNavController().navigate(R.id.action_firstPage_to_secondPage)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}