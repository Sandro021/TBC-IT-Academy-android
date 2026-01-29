package com.example.homework_37

import app.cash.turbine.test
import com.example.homework_37.data.common.Resource
import com.example.homework_37.domain.model.Post
import com.example.homework_37.domain.model.Story
import com.example.homework_37.domain.usecase.GetFeedUseCase
import com.example.homework_37.domain.usecase.GetStoriesUseCase
import com.example.homework_37.presentation.screen.home.HomeViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val getFeedUseCase = mockk<GetFeedUseCase>()
    private val getStoriesUseCase = mockk<GetStoriesUseCase>()
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `loadAllData updates success for both stories and feed`() = runTest {
        val mockStories = listOf(Story(id = 1, name = "Kazbegi", imageUrl = "url1"))

        val mockPosts = listOf(

            Post(
                1,
                "Sandro",
                "https://randomuser.me/api/portraits/women/45.jpg",
                "",
                "",
                listOf("https://eurasia.travel/wp-content/uploads/2025/04/1.-Borjomi-town.jpg"),
                3,
                5
            )
        )

        every { getStoriesUseCase() } returns flowOf(Resource.Success(mockStories))
        every { getFeedUseCase() } returns flowOf(Resource.Success(mockPosts))

        viewModel = HomeViewModel(getFeedUseCase, getStoriesUseCase)
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertEquals(mockStories, state.stories)
            assertEquals(mockPosts, state.posts)
            assertEquals(false, state.isStoriesLoading)
            assertEquals(false, state.isFeedLoading)
        }
    }


    @Test
    fun `loadAllData updates loading states correctly`() = runTest {

        every { getStoriesUseCase() } returns flowOf(Resource.Loading)
        every { getFeedUseCase() } returns flowOf(Resource.Loading)

        viewModel = HomeViewModel(getFeedUseCase, getStoriesUseCase)

        runCurrent()

        viewModel.state.test {
            val state = awaitItem()
            assertEquals(true, state.isStoriesLoading)
            assertEquals(true, state.isFeedLoading)
        }
    }

    @Test
    fun `loadAllData handles stories error while feed succeeds`() = runTest {
        val errorMsg = "Stories API Down"
        val mockPosts = listOf(

            Post(
                1,
                "Sandro",
                "https://randomuser.me/api/portraits/women/45.jpg",
                "",
                "",
                listOf("https://eurasia.travel/wp-content/uploads/2025/04/1.-Borjomi-town.jpg"),
                3,
                5
            )
        )

        every { getStoriesUseCase() } returns flowOf(Resource.Error(errorMsg))
        every { getFeedUseCase() } returns flowOf(Resource.Success(mockPosts))

        viewModel = HomeViewModel(getFeedUseCase, getStoriesUseCase)
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertEquals(errorMsg, state.error)
            assertEquals(mockPosts, state.posts)
            assertEquals(false, state.isStoriesLoading)
        }
    }


    @Test
    fun `loadAllData handles feed error while stories succeed`() = runTest {
        val errorMsg = "Feed API Down"
        val mockStories = listOf(Story(id = 1, name = "Batumi", ""))

        every { getStoriesUseCase() } returns flowOf(Resource.Success(mockStories))
        every { getFeedUseCase() } returns flowOf(Resource.Error(errorMsg))

        viewModel = HomeViewModel(getFeedUseCase, getStoriesUseCase)
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertEquals(errorMsg, state.error)
            assertEquals(mockStories, state.stories)
            assertEquals(false, state.isFeedLoading)
        }
    }
}