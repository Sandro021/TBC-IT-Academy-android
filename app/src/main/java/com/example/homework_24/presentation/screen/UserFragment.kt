package com.example.homework_24.presentation.screen

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework_24.databinding.FragmentUserBinding
import com.example.homework_24.presentation.adapter.UserAdapter
import com.example.homework_24.presentation.common.BaseFragment
import com.example.homework_24.presentation.user.UserIntent
import com.example.homework_24.presentation.user.UserViewModel

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserFragment : BaseFragment<FragmentUserBinding>(FragmentUserBinding::inflate) {

    private val viewModel: UserViewModel by viewModels()
    private lateinit var adapter: UserAdapter
    override fun bind() {
        setUpUserRecyclerView()
        setUpListeners()
        observeState()
    }

    override fun listeners() {

    }

    private fun setUpUserRecyclerView() = with(binding) {
        adapter = UserAdapter { userInfo ->
            viewModel.processIntent(UserIntent.DeleteUser(userInfo.id))
        }
        rvUsers.layoutManager = LinearLayoutManager(requireContext())
        rvUsers.adapter = adapter
    }

    private fun setUpListeners() = with(binding) {
        etName.addTextChangedListener {
            viewModel.processIntent(UserIntent.FirstNameChanged(it.toString()))
        }
        etLastName.addTextChangedListener {
            viewModel.processIntent(UserIntent.LastNameChanged(it.toString()))
        }
        etEmail.addTextChangedListener {
            viewModel.processIntent(UserIntent.EmailChanged(it.toString()))
        }
        btSave.setOnClickListener {
            val email = etEmail.text.toString()

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                return@setOnClickListener
            }
            viewModel.processIntent(UserIntent.SaveClicked)
        }
        btRead.setOnClickListener { viewModel.processIntent(UserIntent.ReadClicked) }
    }

    private fun observeState() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                adapter.submitList(state.users)


                if (etName.text.toString() != state.firstName) {
                    etName.setText(state.firstName)
                }
                if (etLastName.text.toString() != state.lastName) {
                    etLastName.setText(state.lastName)
                }
                if (etEmail.text.toString() != state.email) {
                    etEmail.setText(state.email)
                }
            }
        }
    }
}