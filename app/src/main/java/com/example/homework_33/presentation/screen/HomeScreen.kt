package com.example.homework_33.presentation.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.homework_33.R
import com.example.homework_33.ui.theme.White

val customFont = FontFamily(
    Font(R.font.comfortaa)

)

@Composable
fun HomeScreen(
    onLogin: () -> Unit,
    onRegister: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White),

        )
    {
        Row(
            modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(R.string.photo),
                fontSize = 35.sp,
                fontFamily = customFont
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            Row(modifier = Modifier.padding(start = 20.dp)) {
                Icon(
                    painter = painterResource(R.drawable.pavel2),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(30.dp)

                )
                Spacer(Modifier.width(10.dp))
                Column {
                    Text(
                        stringResource(R.string.pawel_czerwinski),
                        fontSize = 16.sp
                    )
                    Text(
                        stringResource(R.string.pawel_czerwinski_second),
                        fontSize = 16.sp
                    )
                }
            }
            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),

                ) {
                OutlinedButton(
                    onClick = onLogin,
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),

                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(stringResource(R.string.log_in_caps))
                }
                Button(
                    onClick = onRegister,
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    Text(stringResource(R.string.register), color = Color.White)
                }
            }
        }

    }

}


@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen(
        onLogin = {},
        onRegister = {}
    )
}