package com.example.homework_30.presentation.screen.search.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_30.R
import com.example.homework_30.databinding.ItemCategoryBinding
import com.example.homework_30.presentation.model.CategoryUiModel


class SearchAdapter(
    private val onItemClick: (CategoryUiModel) -> Unit
) : ListAdapter<CategoryUiModel, SearchAdapter.SearchViewHolder>(SearchDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SearchViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CategoryUiModel) {
            binding.tvName.text = item.name


            val density = binding.root.context.resources.displayMetrics.density
            val indentPx = (item.level * 24 * density).toInt()

            binding.root.updatePadding(left = indentPx)


            binding.dotsContainer.removeAllViews()
            if (item.dotsCount > 0) {
                binding.dotsContainer.isVisible = true
                repeat(item.dotsCount) {
                    val dotView = View(binding.root.context).apply {
                        setBackgroundResource(R.drawable.bg_orange_dot)
                        val params = LinearLayout.LayoutParams(
                            (8 * density).toInt(),
                            (8 * density).toInt()
                        ).apply {
                            marginEnd = (4 * density).toInt()
                        }
                        layoutParams = params
                    }
                    binding.dotsContainer.addView(dotView)
                }
            } else {
                binding.dotsContainer.isVisible = false
            }


            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    class SearchDiffCallback : DiffUtil.ItemCallback<CategoryUiModel>() {
        override fun areItemsTheSame(oldItem: CategoryUiModel, newItem: CategoryUiModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CategoryUiModel,
            newItem: CategoryUiModel
        ): Boolean {

            return oldItem == newItem && oldItem.isExpanded == newItem.isExpanded
        }
    }
}
