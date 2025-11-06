package com.example.homework_17.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_17.R
import com.example.homework_17.databinding.CardLayoutBinding
import com.example.homework_17.model.CardInfo

class CardPagerAdapter :
    ListAdapter<CardInfo, CardPagerAdapter.CardViewHolder>(CardDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardViewHolder {
        val binding = CardLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CardViewHolder(val binding: CardLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(card: CardInfo) = with(binding) {
            tvCardHolderName.text = card.cardHolderName
            tvCardNumber.text = card.cardNumber.chunked(4).joinToString {" "}
            tvValidThru.text = card.validThru

            when (card.cardType.uppercase()) {
                "VISA" -> cardBackground.setBackgroundResource(R.drawable.visa)
                else -> {
                    cardBackground.setBackgroundResource(R.drawable.mastercard)
                }
            }
        }

    }

    class CardDiffCallback : DiffUtil.ItemCallback<CardInfo>() {
        override fun areItemsTheSame(oldItem: CardInfo, newItem: CardInfo): Boolean =
            oldItem.cardNumber == newItem.cardNumber

        override fun areContentsTheSame(oldItem: CardInfo, newItem: CardInfo): Boolean =
            oldItem == newItem
    }
}