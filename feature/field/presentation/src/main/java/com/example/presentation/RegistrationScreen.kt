package com.example.presentation


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.presentation.contract.RegistrationEffect
import com.example.presentation.contract.RegistrationEvent
import com.example.presentation.model.FieldUiModel
import com.example.theme.theme.AppColor
import com.example.theme.theme.AppTheme
import com.example.theme.theme.Padding
import com.example.theme.theme.Radius
import com.example.theme.theme.Spacer
import kotlinx.coroutines.flow.collectLatest


@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()


    val snackbarHostState = remember { SnackbarHostState() }


    LaunchedEffect(key1 = true) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is RegistrationEffect.ShowSnackbar -> {

                    snackbarHostState.showSnackbar(effect.message)
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = AppTheme.colors.background
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {


            LazyColumn(
                contentPadding = PaddingValues(Padding.padding24),
                verticalArrangement = Arrangement.spacedBy(Spacer.space24),
                modifier = Modifier.fillMaxSize()
            ) {

                items(state.fieldGroups) { group ->
                    FieldGroupCard(
                        fields = group,
                        inputValues = state.inputValues,
                        errors = state.errors,
                        onValueChange = { id, newValue ->
                            viewModel.handleEvent(RegistrationEvent.OnValueChange(id, newValue))
                        }
                    )
                }

                item {
                    Button(
                        onClick = { viewModel.handleEvent(RegistrationEvent.OnRegisterClicked) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.colors.buttonBackground)
                    ) {
                        Text(stringResource(com.example.resource.R.string.register))
                    }
                }
            }

            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun FieldGroupCard(
    fields: List<FieldUiModel>,
    inputValues: Map<Int, String>,
    errors: Map<Int, String>,
    onValueChange: (Int, String) -> Unit
) {
    Card(
        shape = Radius.radius16,
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.cardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = Spacer.space8),
        modifier = Modifier.fillMaxWidth()

    ) {
        Column(modifier = Modifier.padding(Padding.padding16)) {

            fields.forEachIndexed { index, field ->
                DynamicFieldItem(
                    field = field,
                    value = inputValues[field.id] ?: "",
                    error = errors[field.id],
                    onValueChange = { onValueChange(field.id, it) }
                )

                if (index < fields.lastIndex) {
                    HorizontalDivider(color = AppTheme.colors.divider.copy(alpha = 0.5f))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicFieldItem(
    field: FieldUiModel,
    value: String,
    error: String?,
    onValueChange: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {

        Column(modifier = Modifier.weight(1f)) {
            if (field.isChooser) {

                OutlinedTextField(
                    value = value,

                    onValueChange = {},

                    label = { Text(field.label, color = Color.Gray) },

                    enabled = false,

                    modifier = Modifier.fillMaxWidth(),

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AppColor.transparent,
                        unfocusedBorderColor = AppColor.transparent,
                        disabledBorderColor = AppColor.transparent,
                        disabledTextColor = AppTheme.colors.textPrimary,
                        disabledLabelColor = AppTheme.colors.textHint

                    ),
                    isError = error != null
                )
            } else {

                TextField(
                    value = value,

                    onValueChange = onValueChange,

                    label = { Text(field.label) },

                    keyboardOptions = KeyboardOptions(
                        keyboardType = if (field.isNumeric) KeyboardType.Number else KeyboardType.Text
                    ),

                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = AppColor.transparent,
                        unfocusedContainerColor = AppColor.transparent,
                        focusedIndicatorColor = AppColor.transparent,
                        unfocusedIndicatorColor = AppColor.transparent
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    isError = error != null
                )
            }

            if (error != null) {
                Text(
                    text = error,
                    color = AppTheme.colors.error,
                    style = AppTheme.typography.label,
                    modifier = Modifier.padding(start = Padding.padding16)
                )
            }
        }


        AsyncImage(
            model = field.iconUrl,
            contentDescription = null,
            error = painterResource(com.example.resource.R.drawable.default_icon),
            modifier = Modifier
                .size(24.dp)
                .padding(end = Padding.padding8)
        )
    }
}