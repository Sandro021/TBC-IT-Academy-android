package com.example.gymtracker.presentation.screen.home

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gymtracker.databinding.ItemWorkoutSetBinding

class WorkoutSetsAdapter(
    var exerciseId: String,
    private val onWeightChanged: (exerciseId: String, setNumber: Int, value: String) -> Unit,
    private val onRepsChanged: (exerciseId: String, setNumber: Int, value: String) -> Unit
) : ListAdapter<WorkoutSetModel, WorkoutSetsAdapter.VH>(Diff) {

    object Diff : DiffUtil.ItemCallback<WorkoutSetModel>() {
        override fun areItemsTheSame(o: WorkoutSetModel, n: WorkoutSetModel) = o.number == n.number
        override fun areContentsTheSame(o: WorkoutSetModel, n: WorkoutSetModel) = o == n
    }

    class VH(val b: ItemWorkoutSetBinding) : RecyclerView.ViewHolder(b.root) {
        var w: TextWatcher? = null
        var r: TextWatcher? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemWorkoutSetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val set = getItem(position)

        holder.b.tvSetNumber.text = set.number.toString()

        // remove old watchers
        holder.w?.let { holder.b.etWeight.removeTextChangedListener(it) }
        holder.r?.let { holder.b.etReps.removeTextChangedListener(it) }

        // --- Weight ---
        val wEt = holder.b.etWeight
        holder.w?.let { wEt.removeTextChangedListener(it) }
        if (!wEt.hasFocus() && wEt.text.toString() != set.weight) {
            wEt.setText(set.weight)
        }

        // --- Reps ---
        val rEt = holder.b.etReps
        holder.r?.let { rEt.removeTextChangedListener(it) }
        if (!rEt.hasFocus() && rEt.text.toString() != set.reps) {
            rEt.setText(set.reps)
        }

        holder.w = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun afterTextChanged(s: Editable?) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // IMPORTANT: only dispatch if user is actually typing here
                if (holder.b.etWeight.hasFocus()) {
                    onWeightChanged(exerciseId, set.number, s?.toString().orEmpty())
                }
            }
        }

        holder.r = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun afterTextChanged(s: Editable?) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (holder.b.etReps.hasFocus()) {
                    onRepsChanged(exerciseId, set.number, s?.toString().orEmpty())
                }
            }
        }

        holder.b.etWeight.addTextChangedListener(holder.w)
        holder.b.etReps.addTextChangedListener(holder.r)
    }

}