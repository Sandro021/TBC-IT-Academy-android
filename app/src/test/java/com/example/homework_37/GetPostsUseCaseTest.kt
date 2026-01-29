package com.example.homework_37


import app.cash.turbine.test
import com.example.homework_37.data.common.Resource
import com.example.homework_37.domain.model.Post
import com.example.homework_37.domain.repository.PostRepository
import com.example.homework_37.domain.usecase.GetFeedUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetPostsUseCaseTest {


    private val repository: PostRepository = mockk()


    private lateinit var getFeedUseCase: GetFeedUseCase

    @Before
    fun setup() {
        getFeedUseCase = GetFeedUseCase(repository)
    }

    @Test
    fun `invoke should call getStories from repository and emit success`() = runTest {
        // GIVEN
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
            ),
            Post(
                2,
                "Sandroooo",
                "https://randomuser.me/api/portraits/women/45.jpg",
                "",
                "",
                listOf("https://eurasia.travel/wp-content/uploads/2025/04/1.-Borjomi-town.jpg"),
                3,
                5
            )

        )


        every { repository.getFeed() } returns flowOf(Resource.Success(mockPosts))

        // WHEN
        val flow = getFeedUseCase()

        // THEN
        flow.test {
            val result = awaitItem()


            assert(result is Resource.Success)
            assertEquals(mockPosts, (result as Resource.Success).data)


            verify(exactly = 1) { repository.getFeed() }

            awaitComplete()
        }
    }

    @Test
    fun `invoke should return error when repository emits error`() = runTest {
        // GIVEN
        val errorMessage = "Something went wrong"
        every { repository.getFeed() } returns flowOf(Resource.Error(errorMessage))

        // WHEN
        val flow = getFeedUseCase()

        // THEN
        flow.test {
            val result = awaitItem()

            assert(result is Resource.Error)
            assertEquals(errorMessage, (result as Resource.Error).message)

            awaitComplete()
        }
    }
}