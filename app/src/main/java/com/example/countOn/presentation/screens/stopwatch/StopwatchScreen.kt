package com.example.countOn.presentation.screens.stopwatch

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.countOn.R
import com.example.countOn.data.local.Stopwatch
import com.example.countOn.presentation.ui.theme.CountOnTheme



@Composable
fun StopwatchScreen(modifier: Modifier = Modifier) {

    val viewmodel = viewModel<StopwatchViewmodel>()
    val timer by viewmodel.timer.collectAsState()
    val state by viewmodel.state.collectAsState()
    val flag by viewmodel.flag.collectAsState()

    LaunchedEffect(flag) {
        Log.d(
            "flagAdded",
            "count: ${flag.size} -> ${flag.joinToString(transform = { it.toTime() })}"
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            "Stopwatch",
            modifier = Modifier.padding(vertical = 25.dp, horizontal = 15.dp),
            style = MaterialTheme.typography.displaySmall
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(4f)
                .padding(vertical = 50.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                timer.toTime(),
                style = MaterialTheme.typography.displayLarge
            )

            if (flag.isNotEmpty()) FlagList(flag = flag)
        }
        ButtonsRow(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            state = state,
            onFlagStopClicked = viewmodel::onFlagStopClicked,
            onPlayPauseClicked = viewmodel::onPlayPauseClicked,
        )

    }
}

@Composable
fun FlagList(
    flag: List<Stopwatch>
) {
    LazyColumn(
        reverseLayout = true,
        verticalArrangement = Arrangement.Top
    ) {
        itemsIndexed(items = flag) { i, it ->
            val previousFlag = if (i == 0) Stopwatch()
            else flag[i - 1]
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val monoSpacingStyle = LocalTextStyle.current.copy(
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    "%${flag.size.toString().length}s".format(i + 1),
                    style = monoSpacingStyle
                )
                Text(
                    "+ ${it.difference(previousFlag).toTime()}",
                    style = monoSpacingStyle,
                )
                Text(
                    it.toTime(),
                    style = monoSpacingStyle,
                )
            }
        }
    }
    Box(Modifier.fillMaxHeight())
}

@Composable
fun ButtonsRow(
    modifier: Modifier = Modifier,
    state: StopwatchEvent,
    onPlayPauseClicked: () -> Unit = {},
    onFlagStopClicked: () -> Unit = {},
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        if (state != StopwatchEvent.Initial)
            Icon(
                modifier = Modifier
                    .size(75.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onFlagStopClicked)
                    .border(
                        width = 5.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = .4f))
                    .padding(20.dp),
                painter = painterResource(
                    if (state == StopwatchEvent.Running) R.drawable.ic_flag
                    else R.drawable.ic_stop
                ),
                contentDescription = "play",
                tint = MaterialTheme.colorScheme.primary
            )


        Icon(
            modifier = Modifier
                .size(75.dp)
                .clip(CircleShape)
                .clickable(onClick = onPlayPauseClicked)
                .border(
                    width = 5.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
                .background(MaterialTheme.colorScheme.primary.copy(alpha = .4f))
                .padding(20.dp),
            painter = painterResource(
                if (state == StopwatchEvent.Running) R.drawable.ic_pause
                else R.drawable.ic_play
            ),
            contentDescription = "play",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview
@Composable
private fun StopwatchScreenPreview() {
    CountOnTheme {
        Scaffold {
            StopwatchScreen(modifier = Modifier.padding(it))
        }
    }
}