package com.example.homework_36


import com.example.homework_36.domain.model.Tour
import com.example.homework_36.domain.usecase.GetToursUseCase
import com.example.homework_36.presentation.TourViewModel
import com.example.homework_36.presentation.screen.tour.contract.TourIntent
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TourViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getToursUseCase = mockk<GetToursUseCase>()
    private lateinit var viewModel: TourViewModel


    private val mockTour = Tour(
        title = "Safari Adventure",
        location = "Kenya",
        number = "254 123",
        photo = "http://example.com/safari.jpg",
        price = 200,
        stars = 5
    )

    @Before
    fun setUp() {
        viewModel = TourViewModel(getToursUseCase)
    }

    @Test
    fun `onIntent LoadTours updates state with tours on success`() = runTest {

        val mockList = listOf(mockTour)
        coEvery { getToursUseCase() } returns mockList


        viewModel.onIntent(TourIntent.LoadTours)
        advanceUntilIdle()


        val state = viewModel.state.value
        assertFalse("Loading should be false", state.isLoading)
        assertNull("Error should be null", state.error)
        assertEquals("Tours list matches", mockList, state.tours)
    }

    @Test
    fun `onIntent LoadTours updates state with error on failure`() = runTest {

        val errorMessage = "No internet connection"
        coEvery { getToursUseCase() } throws RuntimeException(errorMessage)


        viewModel.onIntent(TourIntent.LoadTours)
        advanceUntilIdle()

        
        val state = viewModel.state.value
        assertFalse("Loading should be false", state.isLoading)
        assertEquals("Error message matches", errorMessage, state.error)
        assertTrue("Tours list should be empty", state.tours.isEmpty())
    }
}