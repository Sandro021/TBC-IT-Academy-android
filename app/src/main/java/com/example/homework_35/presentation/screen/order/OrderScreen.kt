package com.example.homework_35.presentation.screen.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.homework_35.presentation.ui.theme.Color.ColorWhite
import com.example.homework_35.presentation.screen.helper.Loader
import com.example.homework_35.presentation.ui.theme.Padding
import com.example.homework_35.presentation.ui.theme.SpaceBy
import com.example.homework_35.presentation.ui.theme.Spacing
import com.example.homework_35.presentation.screen.order.contract.OrdersEvent
import com.example.homework_35.presentation.screen.order.contract.OrdersState


@Composable
fun OrderScreen(
    viewModel: OrdersViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    OrderScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun OrderScreenContent(
    state: OrdersState,
    onEvent: (OrdersEvent) -> Unit,
) {
    val filtered = remember(state.orders, state.selectedStatus) {
        state.orders.filter { it.status == state.selectedStatus }
    }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isLoading,
        onRefresh = { onEvent(OrdersEvent.Refresh) }
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorWhite)
            .pullRefresh(pullRefreshState)
    )
    {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ColorWhite)
        ) {
            Spacer(Spacing.spacing30)

            ScreenTitle()

            Spacer(Spacing.spacing30)

            Status(
                selected = state.selectedStatus,
                onSelected = { onEvent(OrdersEvent.OnTabSelected(it)) }
            )

            Spacer(Spacing.spacing8)

            when {
                state.isLoading && state.orders.isEmpty() -> Loader()

                state.error != null -> Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(state.error)
                }

                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(Padding.padding16),
                        verticalArrangement = Arrangement.spacedBy(SpaceBy.spaceBy12)
                    ) {
                        items(filtered, key = { it.orderId }) { order ->
                            OrderCard(order = order, onDetails = { })
                        }
                    }
                }
            }


        }
        PullRefreshIndicator(
            refreshing = state.isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}


@Composable
@Preview
fun OrderScreenPreview() {
    OrderScreenContent(
        state = OrdersState(),
        onEvent = {}
    )
}