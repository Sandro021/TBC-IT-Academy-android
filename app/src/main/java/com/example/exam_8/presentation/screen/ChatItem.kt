package com.example.exam_8.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.exam_8.domain.model.ChatUser
import com.example.exam_8.domain.model.MessageType
import com.example.exam_8.presentation.theme.Padding
import com.example.exam_8.R

@Composable
fun ChatItem(user: ChatUser) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Padding.padding12),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            model = user.image,
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            placeholder = painterResource(R.drawable.ic_launcher_background)

        )

        Spacer(Modifier.width(12.dp))

        Column(Modifier.weight(1f)) {
            Text(user.owner, fontWeight = FontWeight.Bold)

            Text(
                text = when {
                    user.isTyping -> "Typing..."
                    else -> user.lastMessage
                },
                maxLines = 1
            )
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(user.lastActive, fontSize = 12.sp)

            if (user.unreadMessages > 0) {
                Box(
                    modifier = Modifier
                        .background(Color.Green, CircleShape)
                        .padding(6.dp)
                ) {
                    Text(user.unreadMessages.toString(), color = Color.White)
                }
            }
        }
    }

}

@Composable
@Preview
private fun ChatItemPreview() {
    ChatItem(
        ChatUser(
            1,
            "https://www.alia.ge/wp-content/uploads/2022/09/grisha.jpg",
            "skami",
            "skami",
            "skami",
            3,
            false,
            MessageType.TEXT
        )
    )

}