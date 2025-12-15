package com.example.gymtracker.presentation.screen.exercise_choose

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gymtracker.databinding.ItemPickExerciseBinding

class PickExercisesAdapter(
    private val onClick: (PickExerciseModel) -> Unit
) : ListAdapter<PickExerciseModel, PickExercisesAdapter.VH>(Diff) {

    object Diff : DiffUtil.ItemCallback<PickExerciseModel>() {
        override fun areItemsTheSame(o: PickExerciseModel, n: PickExerciseModel) = o.id == n.id
        override fun areContentsTheSame(o: PickExerciseModel, n: PickExerciseModel) = o == n
    }

    class VH(val b: ItemPickExerciseBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemPickExerciseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VH(b)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        holder.b.tvName.text = item.name
        holder.b.imgCheck.visibility = if (item.isSelected) View.VISIBLE else View.GONE
        holder.b.root.setOnClickListener { onClick(item) }
    }
}