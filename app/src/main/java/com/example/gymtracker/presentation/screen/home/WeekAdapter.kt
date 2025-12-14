package com.example.gymtracker.presentation.screen.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gymtracker.databinding.ItemWeekDayBinding

class WeekAdapter(
    private val onClick: (WeekDayModel) -> Unit
) : ListAdapter<WeekDayModel, WeekAdapter.ViewHolder>(Diff) {

    object Diff : DiffUtil.ItemCallback<WeekDayModel>() {
        override fun areItemsTheSame(oldItem: WeekDayModel, newItem: WeekDayModel) =
            oldItem.date == newItem.date

        override fun areContentsTheSame(oldItem: WeekDayModel, newItem: WeekDayModel) =
            oldItem == newItem
    }

    class ViewHolder(val b: ItemWeekDayBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val b = ItemWeekDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(b)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.b) {
            tvDayName.text = item.dayName
            tvDayNumber.text = item.dayNumber

            if (item.isSelected) {
                tvDayNumber.setBackgroundResource(com.example.gymtracker.R.drawable.bg_day_selected)
                tvDayNumber.setTextColor(android.graphics.Color.parseColor("#1F232A"))
            } else {
                tvDayNumber.setBackgroundResource(com.example.gymtracker.R.drawable.bg_day_unselected)
                tvDayNumber.setTextColor(android.graphics.Color.parseColor("#BFC5CE"))
            }

            root.setOnClickListener { onClick(item) }
        }
    }
}