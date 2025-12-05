package com.example.homework_27.presentation.screen

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework_27.databinding.FragmentUsersBinding
import com.example.homework_27.presentation.adapter.UsersAdapter
import com.example.homework_27.presentation.common.BaseFragment
import com.example.homework_27.presentation.intent.UsersIntent
import com.example.homework_27.presentation.viewmodel.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UsersFragment : BaseFragment<FragmentUsersBinding>(FragmentUsersBinding::inflate) {

    private val viewModel: UsersViewModel by viewModels()
    private lateinit var adapter: UsersAdapter

    override fun bind() {
        setUpUsersRecycler()
        collectState()

    }

    override fun listeners() {
        setUpListeners()
    }

    private fun setUpUsersRecycler() = with(binding) {
        adapter = UsersAdapter()
        rvUsers.layoutManager = LinearLayoutManager(requireContext())
        rvUsers.adapter = adapter

    }

    private fun setUpListeners() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.processIntent(UsersIntent.PullToRefresh)
        }
    }

    private fun collectState() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

                swipeRefresh.isRefreshing = state.isLoading

                tvOfflineBanner.visibility = if (state.isOffline) View.VISIBLE else View.GONE

                if (state.globalError != null) {
                    tvError.text = state.globalError
                    tvError.visibility = View.VISIBLE
                } else {
                    tvError.visibility = View.GONE
                }
                adapter.submitList(state.users)
            }
        }
    }

}