package com.example.homework_11

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_11.databinding.ItemLayoutBinding

class RecyclerAdapter(
    private val users: MutableMap<String, Userinfo>,
    private val onUserLongClick: ((email: String, user: Userinfo) -> Unit)? = null
) :
    RecyclerView.Adapter<RecyclerAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {
        return UserViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    private val keyList: List<String> get() = users.keys.toList()

    fun addUser(email: String, user: Userinfo) {
        users[email] = user
        val position = keyList.indexOf(email)

        notifyItemChanged(position)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[keyList[position]]

        holder.binding.tvFirstName.text = user?.firstName
        holder.binding.tvLastName.text = user?.lastName
        holder.binding.tvAge.text = user?.age.toString()
        holder.binding.tvEmail.text = keyList[position]

        holder.itemView.setOnLongClickListener {
            user?.let { onUserLongClick?.invoke(keyList[position], it) }
            true
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }


    inner class UserViewHolder(val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}