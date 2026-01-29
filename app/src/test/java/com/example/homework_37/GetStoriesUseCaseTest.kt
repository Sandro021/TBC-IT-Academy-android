package com.example.homework_37

import app.cash.turbine.test
import com.example.homework_37.data.common.Resource
import com.example.homework_37.domain.model.Story
import com.example.homework_37.domain.repository.StoryRepository
import com.example.homework_37.domain.usecase.GetStoriesUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetStoriesUseCaseTest {


    private val repository: StoryRepository = mockk()


    private lateinit var getStoriesUseCase: GetStoriesUseCase

    @Before
    fun setup() {
        getStoriesUseCase = GetStoriesUseCase(repository)
    }

    @Test
    fun `invoke should call getStories from repository and emit success`() = runTest {
        // GIVEN
        val mockStories = listOf(
            Story(id = 1, name = "Kazbegi", imageUrl = "url_1"),
            Story(id = 2, name = "Svaneti", imageUrl = "url_2")
        )


        every { repository.getStories() } returns flowOf(Resource.Success(mockStories))

        // WHEN
        val flow = getStoriesUseCase()

        // THEN
        flow.test {
            val result = awaitItem()


            assert(result is Resource.Success)
            assertEquals(mockStories, (result as Resource.Success).data)


            verify(exactly = 1) { repository.getStories() }

            awaitComplete()
        }
    }

    @Test
    fun `invoke should return error when repository emits error`() = runTest {
        // GIVEN
        val errorMessage = "Something went wrong"
        every { repository.getStories() } returns flowOf(Resource.Error(errorMessage))

        // WHEN
        val flow = getStoriesUseCase()

        // THEN
        flow.test {
            val result = awaitItem()

            assert(result is Resource.Error)
            assertEquals(errorMessage, (result as Resource.Error).message)

            awaitComplete()
        }
    }
}