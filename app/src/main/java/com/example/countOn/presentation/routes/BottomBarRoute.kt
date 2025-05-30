package com.example.countOn.presentation.routes

import androidx.annotation.DrawableRes
import com.example.countOn.R

data class BottomBarRoute(
    val title: String,
    @DrawableRes val icon: Int,
    val route: ScreenRoutes
)

val BOTTOM_APP_BAR_ROUTES = listOf<BottomBarRoute>(
    BottomBarRoute(
        title = "Home",
        icon = R.drawable.ic_home,
        route = ScreenRoutes.HomeScreen,
    ),
    BottomBarRoute(
        title = "Timer",
        icon = R.drawable.ic_timer,
        route = ScreenRoutes.TimerScreen,
    ),
    BottomBarRoute(
        title = "Stopwatch",
        icon = R.drawable.ic_stopwatch,
        route = ScreenRoutes.StopWatchScreen,
    ),
    BottomBarRoute(
        title = "Interval",
        icon = R.drawable.ic_interval,
        route = ScreenRoutes.IntervalScreen,
    ),
)