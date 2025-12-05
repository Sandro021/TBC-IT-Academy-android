package com.example.exam_6.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import com.example.exam_6.databinding.ItemWorkspaceBinding
import com.example.exam_6.domain.Workspace

class WorkspacePagingAdapter : PagingDataAdapter<Workspace,
        WorkspacePagingAdapter.WorkspaceViewHolder>(DiffCallback) {
    object DiffCallback : DiffUtil.ItemCallback<Workspace>() {
        override fun areItemsTheSame(
            oldItem: Workspace,
            newItem: Workspace
        ): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(
            oldItem: Workspace,
            newItem: Workspace
        ): Boolean {
            return oldItem == newItem
        }

    }

    inner class WorkspaceViewHolder(
        private val binding: ItemWorkspaceBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Workspace) = with(binding) {
            tvLocation.text = item.location
            tvAltitude.text = "${item.altitudeMeters} m"
            tvTitle.text = item.title
            ratingBar.rating = item.stars.toFloat()

            imageBackground.load(item.imageUrl) {
                crossfade(true)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkspaceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWorkspaceBinding.inflate(inflater, parent, false)
        return WorkspaceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkspaceViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}