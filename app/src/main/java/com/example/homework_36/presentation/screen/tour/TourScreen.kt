package com.example.homework_36.presentation.screen.tour

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.homework_36.presentation.TourViewModel
import com.example.homework_36.presentation.screen.tour.contract.TourIntent
import com.example.homework_36.presentation.screen.tour.contract.TourState
import com.example.homework_36.presentation.ui.theme.AppColor
import com.example.homework_36.presentation.ui.theme.Padding
import com.example.homework_36.presentation.ui.theme.Space
import kotlin.math.absoluteValue


@Composable
fun TourScreen(
    viewModel: TourViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onIntent(TourIntent.LoadTours)
    }

    TourScreenContent(state)
}


@SuppressLint("FrequentlyChangingValue")
@Composable
private fun TourScreenContent(
    state: TourState,

    ) {
    val pagerState = rememberPagerState { state.tours.size }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColor.BackgroundColor),
        contentAlignment = Alignment.Center
    ) {

        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = Padding.padding54),
            pageSpacing = Space.space16,
            modifier = Modifier.fillMaxSize()
        ) { page ->

            val tour = state.tours[page]

            val pageOffset =
                ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction)
                    .absoluteValue

            val scale = lerp(
                start = 0.85f,
                stop = 1f,
                fraction = 1f - pageOffset.coerceIn(0f, 1f)
            )

            TourCard(
                tour = tour,
                modifier = Modifier.graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
            )
        }
        CustomBottomBar(
            modifier = Modifier.align(Alignment.BottomCenter)
        )

    }
}


@Composable
@Preview
private fun TourScreenContentPreview() {
    TourScreenContent(state = TourState())
}


