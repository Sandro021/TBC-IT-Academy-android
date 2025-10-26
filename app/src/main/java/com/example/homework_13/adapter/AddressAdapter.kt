package com.example.homework_13.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_13.address.Address
import com.example.homework_13.R

class AddressAdapter(
    private val onClick: (Address) -> Unit,
    private val onLongClick: (Address) -> Unit
) : ListAdapter<Address, AddressAdapter.AddressViewHolder>(AddressDiffCallback()) {


    private var selectedAddressId: Long? = null


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddressViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_address, parent, false)
        return AddressViewHolder(view, onClick, onLongClick)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val address = getItem(position)
        val isSelected = address.id == selectedAddressId
        holder.bind(address, isSelected)
    }

    inner class AddressViewHolder(
        itemView: View,
        private val onClick: (Address) -> Unit,
        private val onLongClick: (Address) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val type: TextView = itemView.findViewById(R.id.tvTitle)
        private val details: TextView = itemView.findViewById(R.id.tvAddress)
        private val edit: TextView = itemView.findViewById(R.id.tvEdit)
        private val icon: ImageView = itemView.findViewById(R.id.ivIcon)
        private val radioButton: RadioButton = itemView.findViewById(R.id.rbSelect)

        @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
        fun bind(address: Address, isSelected: Boolean) {
            type.text = "My ${address.type}"
            details.text = address.address

            itemView.setOnLongClickListener {
                onLongClick(address)
                true
            }


            radioButton.isChecked = isSelected
            edit.isEnabled = true


            radioButton.setOnClickListener {
                selectedAddressId = address.id
                notifyDataSetChanged()
            }
            edit.setOnClickListener {
                if (isSelected) {
                    onClick(address)
                }
            }
            when (address.type) {
                "Home" -> icon.setImageResource(R.drawable.house)
                "Office" -> icon.setImageResource(R.drawable.apartment)
            }
        }

    }

    class AddressDiffCallback : DiffUtil.ItemCallback<Address>() {
        override fun areItemsTheSame(oldItem: Address, newItem: Address): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Address, newItem: Address): Boolean =
            oldItem == newItem
    }
}