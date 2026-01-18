package com.example.homework_33.presentation.screen.event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.homework_33.domain.model.EventCategory
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun CategoryRow(
    selected: EventCategory,
    onSelect: (EventCategory) -> Unit
) {
    val categories = listOf(
        EventCategory.ALL,
        EventCategory.PARTY,
        EventCategory.CAMPING,
        EventCategory.OTHER
    )
    LazyRow(
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(categories) { cat ->
            val isSelected = cat == selected
            FilterChip(
                selected = isSelected,
                onClick = { onSelect(cat) },
                label = {
                    Text(
                        text = cat.label,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFF75D7AE),
                    selectedLabelColor = Color(0xFF0E1A1A),
                    containerColor = Color(0xFF2E3D43),
                    labelColor = Color(0xFFC9D3D6)
                ),
                border = null,
                shape = RoundedCornerShape(14.dp),
            )
        }
    }

}


@Preview
@Composable
fun CategoryRowPreview() {
    CategoryRow(
        selected = EventCategory.ALL,
        onSelect = {}
    )
}