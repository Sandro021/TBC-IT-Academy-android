package com.example.gymtracker.presentation.screen.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gymtracker.databinding.ItemWeekDayBinding
import androidx.core.graphics.toColorInt

class CalendarAdapter(
    private val onClick: (CalendarModel) -> Unit
) : ListAdapter<CalendarModel, CalendarAdapter.ViewHolder>(Diff) {

    object Diff : DiffUtil.ItemCallback<CalendarModel>() {
        override fun areItemsTheSame(oldItem: CalendarModel, newItem: CalendarModel) =
            oldItem.date == newItem.date

        override fun areContentsTheSame(oldItem: CalendarModel, newItem: CalendarModel) =
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
                tvDayNumber.setTextColor("#1F232A".toColorInt())
            } else {
                tvDayNumber.setBackgroundResource(com.example.gymtracker.R.drawable.bg_day_unselected)
                tvDayNumber.setTextColor("#BFC5CE".toColorInt())
            }

            root.setOnClickListener { onClick(item) }
        }
    }
}