package com.example.homework_14.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_14.databinding.StatusCategoryBinding
import com.example.homework_14.order.OrderStatus

class CategoryAdapter(
    private val onCategoryClick: (OrderStatus) -> Unit
) : ListAdapter<OrderStatus, CategoryAdapter.CategoryViewHolder>(DiffCallback()) {


    private var selectedStatus: OrderStatus = OrderStatus.PENDING

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        val binding =
            StatusCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CategoryViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class CategoryViewHolder(private val binding: StatusCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(status: OrderStatus) {
            binding.tvCategory.text = status.name
            binding.root.isSelected = (status == selectedStatus)

            binding.root.setOnClickListener {
                selectedStatus = status
                onCategoryClick(status)
                notifyDataSetChanged()
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<OrderStatus>() {
        override fun areItemsTheSame(oldItem: OrderStatus, newItem: OrderStatus) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: OrderStatus, newItem: OrderStatus) =
            oldItem == newItem
    }
}