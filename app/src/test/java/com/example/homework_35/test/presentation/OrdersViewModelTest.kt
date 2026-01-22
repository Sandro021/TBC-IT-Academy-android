package com.example.homework_35.test.presentation

import app.cash.turbine.test
import com.example.homework_35.domain.model.Order
import com.example.homework_35.domain.model.OrderStatus
import com.example.homework_35.domain.usecase.GetOrdersUseCase
import com.example.homework_35.presentation.screen.order.OrdersViewModel
import com.example.homework_35.presentation.screen.order.contract.OrdersEvent
import com.example.homework_35.test.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import org.junit.Assert.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class OrdersViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getOrdersUseCase: GetOrdersUseCase = mockk()


    private fun fakeOrders() = listOf(
        Order(
            orderId = 1524,
            trackingNumber = "IK287368838",
            quantity = 2,
            status = OrderStatus.PENDING,
            date = LocalDate.parse("2021-05-13"),
            subtotal = 110
        ),
        Order(
            orderId = 1528,
            trackingNumber = "IK2873218321",
            quantity = 5,
            status = OrderStatus.DELIVERED,
            date = LocalDate.parse("2021-06-12"),
            subtotal = 337
        )
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `init loads orders successfully`() = runTest {
        val orders = fakeOrders()

        coEvery { getOrdersUseCase.invoke() } returns orders

        val viewmodel = OrdersViewModel(getOrdersUseCase)

        viewmodel.state.test {

            val first = awaitItem()
            assertEquals(false, first.isLoading)
            assertEquals(emptyList<Order>(), first.orders)

            runCurrent()
            val loading = awaitItem()
            assertEquals(true, loading.isLoading)

            runCurrent()
            val success = awaitItem()
            assertEquals(false, success.isLoading)
            assertEquals(orders, success.orders)

            cancelAndIgnoreRemainingEvents()
        }
        coVerify(exactly = 1) { getOrdersUseCase.invoke() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `init loads orders failure sets error`() = runTest {

        coEvery { getOrdersUseCase.invoke() } throws RuntimeException("Boom")


        val viewModel = OrdersViewModel(getOrdersUseCase)


        advanceUntilIdle()

        val final = viewModel.state.value
        assertEquals(false, final.isLoading)
        assertEquals("Boom", final.error)

        coVerify(exactly = 1) { getOrdersUseCase.invoke() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `tab selection updates selectedStatus`() = runTest {

        coEvery { getOrdersUseCase.invoke() } returns emptyList()
        val viewModel = OrdersViewModel(getOrdersUseCase)


        advanceUntilIdle()

        viewModel.onEvent(OrdersEvent.OnTabSelected(OrderStatus.DELIVERED))


        assertEquals(OrderStatus.DELIVERED, viewModel.state.value.selectedStatus)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `refresh calls useCase again`() = runTest {

        coEvery { getOrdersUseCase.invoke() } returnsMany listOf(
            emptyList(),
            fakeOrders()
        )
        val viewModel = OrdersViewModel(getOrdersUseCase)
        advanceUntilIdle()

        viewModel.onEvent(OrdersEvent.Refresh)
        advanceUntilIdle()

        coVerify(exactly = 2) { getOrdersUseCase.invoke() }
    }
}