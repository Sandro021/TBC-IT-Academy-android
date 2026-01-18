package com.example.homework_33.presentation.screen.event

import androidx.annotation.DrawableRes
import com.example.homework_33.R
import com.example.homework_33.domain.model.EventCategory

@DrawableRes
fun eventImageRes(id: String, category: EventCategory): Int {
    return when (category) {
        EventCategory.PARTY -> R.drawable.party
        EventCategory.CAMPING -> R.drawable.camping
        EventCategory.OTHER -> R.drawable.other
        EventCategory.ALL -> R.drawable.other2
    }
}