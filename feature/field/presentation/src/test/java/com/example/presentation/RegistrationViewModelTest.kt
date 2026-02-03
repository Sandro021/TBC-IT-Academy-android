package com.example.presentation


import com.example.domain.model.FieldType
import com.example.domain.model.InputType
import com.example.domain.model.RegistrationField
import com.example.domain.usecase.GetRegistrationFormUseCase
import com.example.presentation.contract.RegistrationEffect
import com.example.presentation.contract.RegistrationEvent
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class RegistrationViewModelTest {


    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()


    private val getRegistrationFormUseCase: GetRegistrationFormUseCase = mockk()


    private lateinit var viewModel: RegistrationViewModel


    private val testField = RegistrationField(
        id = 1,
        hint = "Email",
        type = FieldType.INPUT,
        inputType = InputType.TEXT,
        isRequired = true,
        iconUrl = "url"
    )
    private val testGroup = listOf(testField)

    @Before
    fun setup() {

    }

    @Test
    fun `Given useCase returns success, When init, Then state contains mapped fields and loading is false`() =
        runTest {
            // Given
            coEvery { getRegistrationFormUseCase() } returns Result.success(listOf(testGroup))

            // When
            viewModel = RegistrationViewModel(getRegistrationFormUseCase)
            advanceUntilIdle()

            // Then
            val state = viewModel.state.value
            assertEquals(false, state.isLoading)
            assertEquals(1, state.fieldGroups.size)
            assertEquals(1, state.fieldGroups[0].size)
            assertEquals(
                "Email*",
                state.fieldGroups[0][0].label
            )
        }

    @Test
    fun `Given useCase returns failure, When init, Then emits ShowSnackbar effect`() = runTest {
        // Given
        coEvery { getRegistrationFormUseCase() } returns Result.failure(Exception("Network error"))

        // When
        viewModel = RegistrationViewModel(getRegistrationFormUseCase)
        advanceUntilIdle()

        // Then
        val effect = viewModel.effect.first()
        assertTrue(effect is RegistrationEffect.ShowSnackbar)
        assertEquals("Failed to load form", (effect as RegistrationEffect.ShowSnackbar).message)


        assertEquals(false, viewModel.state.value.isLoading)
    }

    @Test
    fun `Given fields loaded, When OnValueChange, Then inputValues updated and specific error removed`() =
        runTest {

            coEvery { getRegistrationFormUseCase() } returns Result.success(listOf(testGroup))
            viewModel = RegistrationViewModel(getRegistrationFormUseCase)
            advanceUntilIdle()


            viewModel.handleEvent(RegistrationEvent.OnRegisterClicked)
            advanceUntilIdle()
            assertTrue(viewModel.state.value.errors.containsKey(1))


            viewModel.handleEvent(RegistrationEvent.OnValueChange(fieldId = 1, value = "new value"))
            advanceUntilIdle()

            // Then
            val state = viewModel.state.value
            assertEquals("new value", state.inputValues[1])
            assertEquals(false, state.errors.containsKey(1))
        }

    @Test
    fun `Given required field is empty, When OnRegisterClicked, Then emits Error Snackbar and updates error state`() =
        runTest {
            // Given
            coEvery { getRegistrationFormUseCase() } returns Result.success(listOf(testGroup))
            viewModel = RegistrationViewModel(getRegistrationFormUseCase)
            advanceUntilIdle()


            // When
            viewModel.handleEvent(RegistrationEvent.OnRegisterClicked)
            advanceUntilIdle()

            // Then
            val state = viewModel.state.value

            assertTrue(state.errors.containsKey(1))
            assertEquals("Email is required", state.errors[1])


            val effect = viewModel.effect.first()
            assertTrue(effect is RegistrationEffect.ShowSnackbar)
            assertEquals("Please fix errors", (effect as RegistrationEffect.ShowSnackbar).message)
        }

    @Test
    fun `Given all fields valid, When OnRegisterClicked, Then emits Success Snackbar`() = runTest {
        // Given
        coEvery { getRegistrationFormUseCase() } returns Result.success(listOf(testGroup))
        viewModel = RegistrationViewModel(getRegistrationFormUseCase)
        advanceUntilIdle()


        viewModel.handleEvent(RegistrationEvent.OnValueChange(1, "Valid Input"))
        advanceUntilIdle()

        // When
        viewModel.handleEvent(RegistrationEvent.OnRegisterClicked)
        advanceUntilIdle()

        // Then
        val effect = viewModel.effect.first()
        assertTrue(effect is RegistrationEffect.ShowSnackbar)
        assertEquals("Registration Successful", (effect as RegistrationEffect.ShowSnackbar).message)


        assertTrue(viewModel.state.value.errors.isEmpty())
    }
}

class MainDispatcherRule(
    private val testDispatcher: kotlinx.coroutines.test.TestDispatcher = StandardTestDispatcher()
) : TestWatcher() {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}