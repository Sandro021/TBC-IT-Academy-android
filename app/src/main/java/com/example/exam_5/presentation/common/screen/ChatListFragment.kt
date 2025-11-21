package com.example.exam_5.presentation.common.screen

import android.app.AlertDialog
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.exam_5.data.dto.MessageType
import com.example.exam_5.databinding.FragmentChatListBinding
import com.example.exam_5.presentation.common.BaseFragment
import com.example.exam_5.presentation.common.adapter.ChatAdapter
import com.example.exam_5.presentation.common.intent.ChatIntent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChatListFragment : BaseFragment<FragmentChatListBinding>(FragmentChatListBinding::inflate) {
    private val viewModel: ChatViewModel by viewModels()

    private val adapter = ChatAdapter()

    override fun bind() {
        setupRecycler()
        setupSearch()
        setupFilter()
        observeState()
        viewModel.onIntent(ChatIntent.LoadChats)
    }

    override fun listeners() {

    }

    private fun setupRecycler() {
        binding.rvRecycler.adapter = adapter
    }

    private fun setupSearch() {
        binding.etSearch.doOnTextChanged { text, _, _, _ ->
            viewModel.onIntent(ChatIntent.Search(text.toString()))
        }
    }

    private fun setupFilter() {
        binding.btFilter.setOnClickListener {
            showFilterDialog()
        }
    }

    private fun showFilterDialog() {
        val items = MessageType.entries.map { it.name }.toTypedArray()

        AlertDialog.Builder(requireContext())
            .setTitle("Filter by type")
            .setItems(items) { _, index ->
                val type = MessageType.entries[index]
                viewModel.onIntent(ChatIntent.Filter(type))
            }
            .setNeutralButton("Clear") { _, _ ->
                viewModel.onIntent(ChatIntent.Filter(null))
            }
            .show()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->


                    if (state.error != null) {
                        Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
                    }

                    adapter.submitList(state.filteredChats)
                }
            }
        }
    }
}