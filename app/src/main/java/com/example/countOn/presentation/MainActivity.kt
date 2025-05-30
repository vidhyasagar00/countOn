package com.example.countOn.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.countOn.presentation.routes.BOTTOM_APP_BAR_ROUTES
import com.example.countOn.presentation.routes.ScreenRoutes
import com.example.countOn.presentation.screens.home.HomeScreen
import com.example.countOn.presentation.screens.interval.IntervalScreen
import com.example.countOn.presentation.screens.stopwatch.StopwatchScreen
import com.example.countOn.presentation.screens.timer.TimerScreen
import com.example.countOn.presentation.ui.theme.CountOnTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CountOnTheme {
                NavigationScreen(
                    Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun NavigationScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Scaffold(
        modifier = modifier,
        bottomBar = { CountOnBottomNavigation(navController) }
    ) {
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            navController = navController,
            startDestination = ScreenRoutes.HomeScreen
        ) {
            composable<ScreenRoutes.HomeScreen> {
                HomeScreen()
            }
            composable<ScreenRoutes.StopWatchScreen> {
                StopwatchScreen()
            }
            composable<ScreenRoutes.TimerScreen> {
                TimerScreen()
            }
            composable<ScreenRoutes.IntervalScreen> {
                IntervalScreen()
            }
        }
    }
}

@Composable
fun CountOnBottomNavigation(navController: NavController) {
    var selectedTab by remember {
        mutableIntStateOf(0)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(vertical = 15.dp, horizontal = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BOTTOM_APP_BAR_ROUTES.forEachIndexed { index, it ->
            Column (
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .clickable {
                        selectedTab = index
                        navController.navigate(it.route) {
                            popUpTo(ScreenRoutes.HomeScreen)
                            launchSingleTop = true
                        }
                    }
                    .padding(all = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .background(
                            if(selectedTab == index) MaterialTheme.colorScheme.onPrimary.copy(
                                alpha = 0.5f
                            )
                            else MaterialTheme.colorScheme.primary
                        )
                        .padding(10.dp),
                    painter = painterResource(it.icon),
                    contentDescription = "bottom icon ${it.title}",
                    tint = if(selectedTab == index) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    it.title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }
    }
}