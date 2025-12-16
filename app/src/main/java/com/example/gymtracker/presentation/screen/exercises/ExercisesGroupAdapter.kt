package com.example.gymtracker.presentation.screen.exercises


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gymtracker.R
import com.example.gymtracker.databinding.ItemExerciseGroupBinding
import com.example.gymtracker.domain.model.ExerciseGroup

class ExerciseGroupsAdapter(
    private val onClick: (ExerciseGroup) -> Unit
) : ListAdapter<ExerciseGroup, ExerciseGroupsAdapter.VH>(Diff) {

    object Diff : DiffUtil.ItemCallback<ExerciseGroup>() {
        override fun areItemsTheSame(oldItem: ExerciseGroup, newItem: ExerciseGroup) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ExerciseGroup, newItem: ExerciseGroup) =
            oldItem == newItem
    }

    class VH(val binding: ItemExerciseGroupBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemExerciseGroupBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        holder.binding.tvTitle.text = item.title
        holder.binding.tvCount.text = item.exerciseCount.toString()

        holder.binding.imgIcon.setBackgroundResource(R.drawable.splash)

        holder.binding.root.setOnClickListener { onClick(item) }
    }
}
