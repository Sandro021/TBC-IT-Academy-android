package com.example.exam_8.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.exam_8.R
import com.example.exam_8.domain.model.MessageType
import com.example.exam_8.presentation.theme.Padding

@Composable
fun ChatTopBar(
    search: String,
    onSearch: (String) -> Unit,
    onFilter: (MessageType?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Padding.padding8)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = search,
            onValueChange = onSearch,
            placeholder = { Text("Search") },
            modifier = Modifier.weight(1f)
        )
        Box {
            IconButton(onClick = { expanded = true }) {
                Icon(painter = painterResource(R.drawable.search), contentDescription = "null")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("All") },
                    onClick = {
                        onFilter(null)
                        expanded = false
                    }
                )

                DropdownMenuItem(
                    text = { Text("Text") },
                    onClick = {
                        onFilter(MessageType.TEXT)
                        expanded = false
                    }
                )

                DropdownMenuItem(
                    text = { Text("Voice") },
                    onClick = {
                        onFilter(MessageType.VOICE)
                        expanded = false
                    }
                )

                DropdownMenuItem(
                    text = { Text("File") },
                    onClick = {
                        onFilter(MessageType.FILE)
                        expanded = false
                    }
                )
            }
        }
    }
}





@Composable
@Preview
private fun ChatTopBarPreview() {
    ChatTopBar("", {}, {})
}