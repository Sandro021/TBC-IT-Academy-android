package com.example.homework_33.presentation.screen.login

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
import com.example.homework_33.presentation.screen.registration.EditTextField
import com.example.homework_33.presentation.screen.customFont
import com.example.homework_33.presentation.screen.login.contract.LoginEffect
import com.example.homework_33.presentation.screen.login.contract.LoginEvent
import com.example.homework_33.ui.theme.White


@Composable
fun LoginScreen(
    onNext: () -> Unit,
    onBack: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { eff ->
            when (eff) {
                is LoginEffect.NavigateHome -> onNext()
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
            text = stringResource(R.string.log_in),
            modifier = Modifier.padding(15.dp),
            fontFamily = customFont,
            fontSize = 40.sp
        )
        EditTextField(
            state.email, onValueChanged = {
                viewModel.onEvent(LoginEvent.EmailChanged(it))
            },
            stringResource(R.string.jane_example_com)
        )
        Spacer(Modifier.height(20.dp))
        EditTextField(
            state.password, onValueChanged = { viewModel.onEvent(LoginEvent.PasswordChanged(it)) },
            stringResource(R.string.password_hint),

        )

        Spacer(Modifier.height(20.dp))
        Button(
            onClick = { viewModel.onEvent(LoginEvent.Submit) },
            modifier = Modifier
                .height(50.dp)
                .padding(horizontal = 10.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {

            Text(stringResource(R.string.log_in))

        }
        state.error?.let { msg ->
            Spacer(Modifier.height(12.dp))
            Text(
                text = msg,
                color = Color.Red,
                modifier = Modifier.padding(horizontal = 15.dp)
            )
        }
    }
}


@Composable
@Preview
fun LoginScreenPreview() {
    LoginScreen(onNext = {}, onBack = {})
}

