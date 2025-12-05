package com.example.homework_27.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.placeholder
import com.example.homework_27.R
import com.example.homework_27.databinding.ItemUserBinding
import com.example.homework_27.presentation.model.UserModel

class UsersAdapter : ListAdapter<UserModel, UsersAdapter.UsersViewHolder>(DiffCallback) {


    object DiffCallback : DiffUtil.ItemCallback<UserModel>() {
        override fun areItemsTheSame(
            oldItem: UserModel,
            newItem: UserModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: UserModel,
            newItem: UserModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class UsersViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserModel) = with(binding) {
            tvName.text = item.name
            tvStatus.text = "${item.activationStatus} • ${item.lastActiveDescription}"
            imgAvatar.load(item.profileImageUrl) {
                placeholder(R.drawable.downloading)
                error(R.drawable.failed)
            }
            if (item.saveError != null) {
                tvSaveError.visibility = View.VISIBLE
                tvSaveError.text = item.saveError
            } else {
                tvSaveError.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder =
        UsersViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}