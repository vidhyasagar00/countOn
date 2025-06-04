package com.example.countOn.presentation.screens.stopwatch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countOn.data.local.Stopwatch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StopwatchViewmodel @Inject constructor(): ViewModel() {

    private val _timer = MutableStateFlow<Stopwatch>(Stopwatch())
    val timer = _timer.asStateFlow()

    private val _state = MutableStateFlow<StopwatchEvent>(StopwatchEvent.Initial)
    val state = _state.asStateFlow()

    private val _flag = MutableStateFlow<List<Stopwatch>>(emptyList())
    val flag = _flag.asStateFlow()

    fun onPlayPauseClicked() {
        viewModelScope.launch {
            when(state.value) {
                StopwatchEvent.Initial -> {
                    _state.emit(StopwatchEvent.Running)
                    startTimer()
                }
                StopwatchEvent.Running -> {
                    _state.emit(StopwatchEvent.Paused)
                }
                StopwatchEvent.Paused -> {
                    _state.emit(StopwatchEvent.Running)
                    startTimer()
                }
            }
        }
    }

    private suspend fun startTimer() {
        withContext(Dispatchers.IO) {
            while (state.value == StopwatchEvent.Running) {
                delay(10)
                if (state.value != StopwatchEvent.Running) break
                _timer.emit(timer.value.increaseSec())
            }
        }
    }

    fun onFlagStopClicked() {
        viewModelScope.launch {
            when(state.value) {
                StopwatchEvent.Running -> {
                    _flag.emit(_flag.value + timer.value)
                }
                StopwatchEvent.Paused -> {
                    _flag.emit(emptyList())
                    _timer.emit(Stopwatch())
                    _state.emit(StopwatchEvent.Initial)
                }
                else -> Unit
            }
        }
    }
}