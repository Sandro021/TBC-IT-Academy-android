package com.example.gymtracker.presentation.screen.home


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gymtracker.databinding.ItemWorkoutRowBinding

class WorkoutItemsAdapter(
    private val onAddSetClicked: (String) -> Unit,
    private val onSetWeightChanged: (exerciseId: String, setNumber: Int, value: String) -> Unit,
    private val onSetRepsChanged: (exerciseId: String, setNumber: Int, value: String) -> Unit,
    private val onLongPressDelete: (WorkoutItemModel) -> Unit
) : ListAdapter<WorkoutItemModel, WorkoutItemsAdapter.VH>(Diff) {

    object Diff : DiffUtil.ItemCallback<WorkoutItemModel>() {
        override fun areItemsTheSame(oldItem: WorkoutItemModel, newItem: WorkoutItemModel) =
            oldItem.exerciseId == newItem.exerciseId

        override fun areContentsTheSame(oldItem: WorkoutItemModel, newItem: WorkoutItemModel) =
            oldItem == newItem
    }

    class VH(val b: ItemWorkoutRowBinding) : RecyclerView.ViewHolder(b.root) {
        var setsAdapter: WorkoutSetsAdapter? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemWorkoutRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)

        holder.b.tvName.text = item.name

        holder.b.btnAddSet.setOnClickListener {
            onAddSetClicked(item.exerciseId)
        }

        if (holder.setsAdapter == null) {
            holder.b.rvSets.layoutManager = LinearLayoutManager(holder.itemView.context)
            holder.b.rvSets.itemAnimator = null // ✅ reduce focus/cursor glitches in nested RV
            holder.b.rvSets.setHasFixedSize(true)

            holder.setsAdapter = WorkoutSetsAdapter(
                onWeightChanged = onSetWeightChanged,
                onRepsChanged = onSetRepsChanged
            )
            holder.b.rvSets.adapter = holder.setsAdapter
        }

        holder.setsAdapter?.submitList(item.sets)

        holder.b.root.setOnLongClickListener {
            onLongPressDelete(item)
            true
        }
    }
}

