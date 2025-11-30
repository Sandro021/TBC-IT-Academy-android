package com.example.homework_20.presentation.common.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework_20.databinding.ItemUserBinding
import com.example.homework_20.domain.model.User

class UsersPagingAdapter : PagingDataAdapter<User, UsersPagingAdapter.UserViewHolder>(DIFF) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(
                oldItem: User,
                newItem: User
            ): Boolean = oldItem.id == newItem.id


            override fun areContentsTheSame(
                oldItem: User,
                newItem: User
            ): Boolean = oldItem == newItem

        }
    }

    class UserViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(user: User?) = with(binding) {
            tvName.text = "${user?.firstName} ${user?.lastName}"
            tvEmail.text = user?.email
            Glide.with(ivAvatar).load(user?.avatar).into(ivAvatar)
        }
    }

}