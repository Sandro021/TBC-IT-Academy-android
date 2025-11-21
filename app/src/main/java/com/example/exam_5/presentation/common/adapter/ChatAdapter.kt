package com.example.exam_5.presentation.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.exam_5.R
import com.example.exam_5.data.dto.ChatItemDto
import com.example.exam_5.data.dto.MessageType
import com.example.exam_5.databinding.ChatItemBinding

class ChatAdapter : ListAdapter<ChatItemDto, ChatAdapter.ChatViewHolder>(DiffCallBack) {

    inner class ChatViewHolder(private val binding: ChatItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChatItemDto) = with(binding) {
            if (item.image.isNullOrEmpty()) {
                ivIcon.setImageResource(R.drawable.ic_launcher_background)
            } else {
                Glide.with(root).load(item.image).placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background).into(ivIcon)
            }
            tvName.text = item.owner
            if (item.isTyping) {
                tvLastMessage.text = "Typing"
            } else {
                tvLastMessage.text = item.lastMessage
            }
            tvTime.text = item.lastActive

            if (item.unreadMessages > 0) {
                tvCount.isVisible = true
                tvCount.text = item.unreadMessages.toString()
            } else {
                tvCount.isVisible = false
            }
            when (MessageType.from(item.lastMessageType)) {
                MessageType.TEXT -> {
                    ivType.isVisible = false
                }

                MessageType.FILE -> {
                    ivType.setImageResource(R.drawable.file)
                    tvLastMessage.text = "Sent an attachment"
                }

                MessageType.VOICE -> {
                    ivType.setImageResource(R.drawable.voice)
                    tvLastMessage.text = "Sent a voice message"
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ChatItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<ChatItemDto>() {
        override fun areItemsTheSame(oldItem: ChatItemDto, newItem: ChatItemDto): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ChatItemDto, newItem: ChatItemDto): Boolean =
            oldItem == newItem
    }
}