package com.example.homework_20.presentation.common.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework_20.data.dto.UserDto
import com.example.homework_20.databinding.ItemUserBinding

class UsersPagingAdapter : PagingDataAdapter<UserDto, UsersPagingAdapter.UserViewHolder>(DIFF) {
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
        private val DIFF = object : DiffUtil.ItemCallback<UserDto>() {
            override fun areItemsTheSame(
                oldItem: UserDto,
                newItem: UserDto
            ): Boolean = oldItem.id == newItem.id


            override fun areContentsTheSame(
                oldItem: UserDto,
                newItem: UserDto
            ): Boolean = oldItem == newItem

        }
    }

    class UserViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(user: UserDto?) = with(binding) {
            tvName.text = "${user?.firstName} ${user?.lastName}"
            tvEmail.text = user?.email
            Glide.with(ivAvatar).load(user?.avatar).into(ivAvatar)
        }
    }

}