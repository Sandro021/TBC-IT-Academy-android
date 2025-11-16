package com.example.homework_20.presentation.common.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework_20.data.dto.UserDto
import com.example.homework_20.databinding.ItemUserBinding

class UsersAdapter : ListAdapter<UserDto, UsersAdapter.UserViewHolder>(
    object : DiffUtil.ItemCallback<UserDto>() {
        override fun areItemsTheSame(old: UserDto, new: UserDto) = old.id == new.id
        override fun areContentsTheSame(old: UserDto, new: UserDto) = old == new
    }
) {


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class UserViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(user: UserDto) = with(binding) {
            tvName.text = "${user.firstName} ${user.lastName}"
            tvEmail.text = user.email
            Glide.with(ivAvatar).load(user.avatar).into(ivAvatar)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = UserViewHolder(
        ItemUserBinding.inflate(LayoutInflater.from(parent.context))
    )
}