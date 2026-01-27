package com.example.homework_36


import com.example.homework_36.domain.repository.TourRepository
import com.example.homework_36.domain.model.Tour
import com.example.homework_36.domain.usecase.GetToursUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetToursUseCaseTest {
    
    private val repository = mockk<TourRepository>()


    private val useCase = GetToursUseCase(repository)

    @Test
    fun `invoke calls repository and returns list of tours`() = runTest {

        val expectedTours = listOf(
            Tour(
                title = "Mountain Hike",
                location = "Alps",
                number = "123-456",
                photo = "url1",
                price = 100,
                stars = 5
            ),
            Tour(
                title = "City Walk",
                location = "London",
                number = "987-654",
                photo = "url2",
                price = 50,
                stars = 4
            )
        )

        coEvery { repository.getTours() } returns expectedTours


        val result = useCase()


        assertEquals(expectedTours, result)

        coVerify(exactly = 1) { repository.getTours() }
    }
}