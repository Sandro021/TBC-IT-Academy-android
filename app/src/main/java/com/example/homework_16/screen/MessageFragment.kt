package com.example.homework_16.screen

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework_16.adapter.ChatAdapter
import com.example.homework_16.common.BaseFragment
import com.example.homework_16.databinding.FragmentMessageBinding
import com.example.homework_16.viewModel.ChatViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MessageFragment : BaseFragment<FragmentMessageBinding>(FragmentMessageBinding::inflate) {

    private val viewModel: ChatViewModel by viewModels()
    private val adapter by lazy { ChatAdapter() }

    override fun bind() {
        setUp()
    }

    override fun listeners() {}

    private fun setUp() = with(binding) {
        val recyclerView = rvItems
        val etMessage = etMessage
        val btSend = btSend

        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.messages.collectLatest { messages ->
                    adapter.submitList(messages)
                    recyclerView.scrollToPosition(0)
                }
            }
        }
        btSend.setOnClickListener {
            val text = etMessage.text.toString()
            if (text.isNotBlank()) {
                viewModel.sendMessage(text)
                etMessage.text?.clear()
            }
        }
    }

}