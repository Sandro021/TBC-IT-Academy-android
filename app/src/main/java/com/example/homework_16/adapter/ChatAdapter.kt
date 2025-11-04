package com.example.homework_16.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_16.R
import com.example.homework_16.message_info.Message

class ChatAdapter(
) : ListAdapter<Message, RecyclerView.ViewHolder>(DiffCallback()) {

    private val left = 0

    private val right = 1

    override fun getItemViewType(position: Int): Int {
        val reverseIndex = itemCount - 1 - position
        return if ((reverseIndex + 1) % 2 == 0) left else right
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (viewType == left) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.message_left, parent, false)
            LeftViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.message_right, parent, false)
            RightViewHolder(view)
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val message = getItem(position)
        if (holder is LeftViewHolder) {
            holder.tvMessage.text = message.text
            holder.tvDate.text = message.formattedTime
        } else if (holder is RightViewHolder) {
            holder.tvMessage.text = message.text
            holder.tvDate.text = message.formattedTime
        }
    }


    inner class LeftViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMessage: TextView = view.findViewById(R.id.tvLeftMessage)
        val tvDate : TextView = view.findViewById(R.id.tvDate)
    }

    inner class RightViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMessage: TextView = view.findViewById(R.id.tvRightMessage)
        val tvDate : TextView = view.findViewById(R.id.tvDate)
    }

    class DiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {

            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
    }

}