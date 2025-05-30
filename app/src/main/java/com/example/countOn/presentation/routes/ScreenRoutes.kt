package com.example.countOn.presentation.routes

import kotlinx.serialization.Serializable

sealed class ScreenRoutes {
    @Serializable object HomeScreen : ScreenRoutes()
    @Serializable object TimerScreen : ScreenRoutes()
    @Serializable object StopWatchScreen : ScreenRoutes()
    @Serializable object IntervalScreen : ScreenRoutes()
}