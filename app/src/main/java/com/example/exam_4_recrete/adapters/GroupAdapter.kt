package com.example.exam_4_recrete.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exam_4_recrete.databinding.ItemGroupBinding
import com.example.exam_4_recrete.model.FormGroup

class GroupAdapter(
    private val groups: List<FormGroup>,
    private val onValueChanged: (Int, String) -> Unit
) :
    RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GroupViewHolder {
        val binding = ItemGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GroupViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: GroupViewHolder,
        position: Int
    ) {
        val group = groups[position]
        val fieldAdapter = FieldAdapter(group.fields , onValueChanged)
        holder.binding.innerRecyclerView.apply {
            layoutManager = LinearLayoutManager(holder.itemView.context)
            adapter = fieldAdapter
            setHasFixedSize(false)
            isNestedScrollingEnabled = false
        }
    }

    override fun getItemCount() = groups.size


    inner class GroupViewHolder(val binding: ItemGroupBinding) :
        RecyclerView.ViewHolder(binding.root)
}