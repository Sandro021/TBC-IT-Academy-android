package com.example.gymtracker.presentation.screen.home


import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gymtracker.databinding.ItemWorkoutSetBinding

class WorkoutSetsAdapter(
    private val onWeightChanged: (exerciseId: String, setNumber: Int, value: String) -> Unit,
    private val onRepsChanged: (exerciseId: String, setNumber: Int, value: String) -> Unit
) : ListAdapter<WorkoutSetModel, WorkoutSetsAdapter.VH>(Diff) {


    object Diff : DiffUtil.ItemCallback<WorkoutSetModel>() {
        override fun areItemsTheSame(o: WorkoutSetModel, n: WorkoutSetModel) =
            o.exerciseId == n.exerciseId && o.number == n.number

        override fun areContentsTheSame(o: WorkoutSetModel, n: WorkoutSetModel) = o == n
    }


    class VH(val b: ItemWorkoutSetBinding) : RecyclerView.ViewHolder(b.root) {
        var weightWatcher: TextWatcher? = null
        var repsWatcher: TextWatcher? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemWorkoutSetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val set = getItem(position)

        holder.b.tvSetNumber.text = set.number.toString()

        val wEt = holder.b.etWeight
        val rEt = holder.b.etReps


        holder.weightWatcher?.let { wEt.removeTextChangedListener(it) }
        holder.repsWatcher?.let { rEt.removeTextChangedListener(it) }
        wEt.onFocusChangeListener = null
        rEt.onFocusChangeListener = null


        if (!wEt.hasFocus() && wEt.text.toString() != set.weight) {
            wEt.setText(set.weight)
        }
        if (!rEt.hasFocus() && rEt.text.toString() != set.reps) {
            rEt.setText(set.reps)
        }


        if (wEt.hasFocus()) wEt.setSelection(wEt.text?.length ?: 0)
        if (rEt.hasFocus()) rEt.setSelection(rEt.text?.length ?: 0)


        holder.weightWatcher = wEt.doAfterTextChanged { }
        holder.repsWatcher = rEt.doAfterTextChanged { }


        wEt.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                onWeightChanged(set.exerciseId, set.number, wEt.text.toString())
            }
        }

        rEt.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (rEt.text.toString() == "0") {
                    rEt.setText("")
                }
                rEt.setSelection(rEt.text?.length ?: 0)
            } else {
                onRepsChanged(set.exerciseId, set.number, rEt.text.toString())
            }
        }
    }
}