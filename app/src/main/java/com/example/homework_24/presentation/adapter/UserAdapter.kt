package com.example.homework_24.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_24.data.datastore.UserInfo
import com.example.homework_24.databinding.UserBinding

class UserAdapter(private val onItemLongClick: (UserInfo) -> Unit) :
    ListAdapter<UserInfo, UserAdapter.UserViewHolder>(UserDiffCallBack()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {
        val binding = UserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: UserViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }


    inner class UserViewHolder(private val binding: UserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserInfo) = with(binding) {
            tvFirstName.text = user.firstName
            tvLastName.text = user.lastName
            tvEmail.text = user.email

            root.setOnLongClickListener {
                onItemLongClick(user)
                true
            }
        }
    }

}

class UserDiffCallBack : DiffUtil.ItemCallback<UserInfo>() {

    override fun areItemsTheSame(oldItem: UserInfo, newItem: UserInfo): Boolean {

        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserInfo, newItem: UserInfo): Boolean {

        return oldItem == newItem
    }
}