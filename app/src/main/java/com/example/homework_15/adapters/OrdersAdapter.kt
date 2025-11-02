package com.example.homework_15.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_15.R
import com.example.homework_15.databinding.OrderDetailsBinding
import com.example.homework_15.orderInfo.Order

class OrdersAdapter(
    private val onLeaveReviewClicked: (Order) -> Unit

) : ListAdapter<Order, OrdersAdapter.OrderViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderViewHolder {
        val binding =
            OrderDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {

            ivProduct.setImageResource(item.imageResId)
            tvName.text = item.title
            tvColor.text = item.color + " |"
            tvPrice.text = item.price
            tvStatus.text = item.status.toString()
            tvQuantity.text = "Qty = " + item.quantity.toString()
            indicator.setBackgroundResource(item.indicator)

            btLeaveReview.text =
                if (item.hasReview) "Buy again" else "Leave Review"
            btLeaveReview.setOnClickListener {
                if (!item.hasReview) onLeaveReviewClicked(item)
                else Toast.makeText(root.context, R.string.Buy_again, Toast.LENGTH_SHORT).show()
            }
        }
    }

    inner class OrderViewHolder(val binding: OrderDetailsBinding) :
        RecyclerView.ViewHolder(binding.root)


    class DiffCallback : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Order, newItem: Order) = oldItem == newItem
    }

}