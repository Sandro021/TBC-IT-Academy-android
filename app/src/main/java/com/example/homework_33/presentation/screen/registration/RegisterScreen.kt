package com.example.homework_33.presentation.screen.registration

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.homework_33.R
import com.example.homework_33.presentation.screen.customFont
import com.example.homework_33.presentation.screen.registration.contract.RegistrationEffect
import com.example.homework_33.presentation.screen.registration.contract.RegistrationEvent
import com.example.homework_33.ui.theme.White


@Composable
fun RegisterScreen(
    onNext: () -> Unit,
    onBack: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { eff ->
            when (eff) {
                RegistrationEffect.NavigateToNext -> onNext()
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
            value = state.email,
            onValueChanged = { viewModel.onEvent(RegistrationEvent.EmailChanged(it)) },
            stringResource(R.string.jane_example_com)
        )
        Spacer(Modifier.height(20.dp))
        EditTextField(
            value = state.password,
            onValueChanged = { viewModel.onEvent(RegistrationEvent.PasswordChanged(it)) },
            stringResource(R.string.password_hint)
        )

        Spacer(Modifier.height(20.dp))
        Button(
            onClick = { viewModel.onEvent(RegistrationEvent.Submit) },
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
fun RegisterScreenPreview() {
    RegisterScreen(onNext = { }, onBack = {})
}

@Composable
fun EditTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    hint: String,
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        placeholder = { Text(text = hint, fontSize = 16.sp) },
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 16.sp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .padding(horizontal = 10.dp),

        shape = RoundedCornerShape(0.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            cursorColor = Color.Black
        )
    )
}