package com.example.homework_12.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_12.R
import androidx.core.graphics.toColorInt

class CategoryAdapter(
    private val categories: List<String>,
    private val onCategoryClick: (String) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var selectedPosition = 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category , parent,false)

        return CategoryViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(
        holder: CategoryViewHolder,
        position: Int
    ) {
        val category = categories[position]
        holder.text.text = category

        val cardView = holder.itemView as com.google.android.material.card.MaterialCardView

        if (position == selectedPosition) {
            cardView.setCardBackgroundColor("#5EE6A9".toColorInt()) // light green
            holder.text.setTextColor("#FFFFFF".toColorInt())
        } else {

            cardView.setCardBackgroundColor("#2E3A44".toColorInt())
            holder.text.setTextColor("#96A7AF".toColorInt())
        }

        holder.itemView.setOnClickListener {
            val adapterPos = holder.bindingAdapterPosition
            if (adapterPos != RecyclerView.NO_POSITION) {
                selectedPosition = adapterPos
                notifyDataSetChanged()
                onCategoryClick(categories[adapterPos])
            }
        }
    }

    override fun getItemCount() = categories.size

    inner class CategoryViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val text : TextView = itemView.findViewById(R.id.tvCategory)
    }
}


