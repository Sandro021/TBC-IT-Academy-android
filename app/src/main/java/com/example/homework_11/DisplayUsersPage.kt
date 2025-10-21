package com.example.homework_11

import android.graphics.Color
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework_11.databinding.FragmentDisplayUsersPageBinding
import kotlin.getValue


class DisplayUsersPage : BaseFragment<FragmentDisplayUsersPageBinding>(
    FragmentDisplayUsersPageBinding::inflate
) {

    private val viewModel: SharedViewModel by activityViewModels()
    private lateinit var adapter : RecyclerAdapter

    override fun bind() {
        setUp()

    }

    private fun setUp() {
        addNewUser()
        updateActiveUserCount()
        updateRemovedUsers()
        updateStatusMessage()
        setUpAdapter()
    }
    private fun setUpAdapter() {
        adapter = RecyclerAdapter(viewModel.users) {email , selectedEmail ->
            viewModel.selectedUserMail = selectedEmail.toString()
            viewModel.mode = getString(R.string.update)
            findNavController().navigate(R.id.action_displayUsersPage_to_addUserPage)
        }
        binding.rvUsers.adapter = adapter
        binding.rvUsers.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun addNewUser() {
        binding.btAddUsers.setOnClickListener {
            viewModel.mode = getString(R.string.add)
            findNavController().navigate(R.id.action_displayUsersPage_to_addUserPage)
        }
    }

    private fun updateActiveUserCount() {
        binding.tvActiveMembers.text = getString(R.string.active_members, viewModel.users.size)
    }

    private fun updateRemovedUsers() {
        binding.tvDeletedMembers.text = getString(R.string.removed_users, viewModel.removedCount)
    }


    private fun updateStatusMessage() = with(binding) {
        if (viewModel.added) {
            tvStatus.text = getString(R.string.user_added_successfully)
            tvStatus.setTextColor(Color.GREEN)
            tvStatus.visibility = View.VISIBLE
            viewModel.added = false
        }
        if (viewModel.updated) {
            tvStatus.text = getString(R.string.user_updated_successfully)
            tvStatus.setTextColor(Color.GREEN)
            tvStatus.visibility = View.VISIBLE
            viewModel.updated = false
        }
        if (viewModel.deleted) {
            tvStatus.text = getString(R.string.user_deleted_successfully)
            tvStatus.setTextColor(Color.GREEN)
            tvStatus.visibility = View.VISIBLE
            viewModel.updated = false
        }
    }

}