package com.example.homework_28.presentation.feed.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import coil3.request.transformations
import coil3.transform.CircleCropTransformation
import com.example.homework_28.databinding.ItemPostBinding
import com.example.homework_28.domain.model.Post
import com.example.homework_28.presentation.extension.toFeedDateString

class PostsAdapter : ListAdapter<Post, PostsAdapter.PostViewHolder>(DiffCallback) {


    object DiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
            oldItem.authorName == newItem.authorName &&
                    oldItem.postDateMillis == newItem.postDateMillis

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
            oldItem == newItem
    }

    inner class PostViewHolder(val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PostViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(
        holder: PostViewHolder,
        position: Int
    ) {
        val post = getItem(position)
        with(holder.binding) {
            tvAuthor.text = post.authorName
            tvDate.text = post.postDateMillis.toFeedDateString()
            tvDesc.text = post.description
            tvComments.text = "${post.commentsCount} Comments"
            tvLikes.text = "${post.likesCount} Likes"

            imgAvatar.load(post.avatarUrl) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            val images = post.images
            layoutImages.visibility = if (images.isEmpty()) View.GONE
            else View.VISIBLE

            cardImg1.visibility = View.GONE
            cardImg2.visibility = View.GONE
            cardImg3.visibility = View.GONE

            if (images.isNotEmpty()) {
                cardImg1.visibility = View.VISIBLE
                img1.load(images[0]) { crossfade(true) }
            }
            if (images.size >= 2) {
                cardImg2.visibility = View.VISIBLE
                img2.load(images[1]) { crossfade(true) }
            }
            if (images.size >= 3) {
                cardImg3.visibility = View.VISIBLE
                img3.load(images[2]) { crossfade(true) }
            }
            commentBox.visibility =
                if (post.canComment) View.VISIBLE
                else View.GONE

            btnAddPhoto.visibility =
                if (post.canPostPhoto) View.VISIBLE
                else View.GONE

            if (!post.canComment) {
                etComment.setText("")
            }
        }
    }
}