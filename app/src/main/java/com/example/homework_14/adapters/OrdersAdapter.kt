package com.example.homework_14.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_14.databinding.OrderDetailsBinding
import com.example.homework_14.order.OrderInfo

class OrdersAdapter(
    private val onDetailsClick: (OrderInfo) -> Unit
) : ListAdapter<OrderInfo, OrdersAdapter.OrderViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderViewHolder {
        val binding =
            OrderDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: OrderViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class OrderViewHolder(private val binding: OrderDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(order: OrderInfo) = with(binding) {
            tvOrderId.text = "Order #${order.id}"
            tvTrackingNumber.text = order.trackingNumber
            tvQuantity.text = "Quantity: ${order.quantity}"
            tvSubtotal.text = "Subtotal: $${order.subTotal}"
            tvStatus.text = order.status.name.lowercase().replaceFirstChar { it.uppercase() }
            tvDate.text = order.date

            btDetails.setOnClickListener {
                onDetailsClick(order)
            }
        }
    }


    class DiffCallback : DiffUtil.ItemCallback<OrderInfo>() {

        override fun areItemsTheSame(
            oldItem: OrderInfo,
            newItem: OrderInfo
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: OrderInfo,
            newItem: OrderInfo
        ): Boolean {
            return oldItem == newItem
        }
    }
}