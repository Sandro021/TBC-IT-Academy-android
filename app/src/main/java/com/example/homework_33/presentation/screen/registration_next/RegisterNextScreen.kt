package com.example.homework_33.presentation.screen.registration_next

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.homework_33.R
import com.example.homework_33.presentation.screen.customFont
import com.example.homework_33.presentation.screen.registration.EditTextField
import com.example.homework_33.presentation.screen.registration_next.contract.RegistrationNextEffect
import com.example.homework_33.presentation.screen.registration_next.contract.RegistrationNextEvent
import com.example.homework_33.ui.theme.White


@Composable
fun RegisterNextScreen(
    onSignUp: () -> Unit,
    onBack: () -> Unit,
    viewModel: RegisterNextViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { eff ->
            when (eff) {
                RegistrationNextEffect.NavigateToHome -> onSignUp()
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {

        Icon(
            painter = painterResource(R.drawable.back_arrow),
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier
                .padding(20.dp)
                .size(30.dp)
                .clickable { onBack() }
        )
        Text(
            text = stringResource(R.string.register_no_caps),
            modifier = Modifier.padding(15.dp),
            fontFamily = customFont,
            fontSize = 40.sp
        )
        EditTextField(
            state.username,
            onValueChanged = { viewModel.onEvent(RegistrationNextEvent.UsernameChanged(it)) },
            stringResource(R.string.user_name)
        )

        Spacer(Modifier.height(20.dp))
        Button(
            onClick = { viewModel.onEvent(RegistrationNextEvent.SubmitRegistration) },
            modifier = Modifier
                .height(50.dp)
                .padding(horizontal = 10.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text(stringResource(R.string.next), color = Color.White)
        }

        state.error?.let { Text(it, color = Color.Red) }
    }
}


@Composable
@Preview
fun RegisterNextScreenPreview() {
    RegisterNextScreen(onSignUp = {}, onBack = {})
}
