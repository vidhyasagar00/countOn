package com.example.countOn.data.local

data class Stopwatch(
    val hours: Int = 0,
    val minutes: Int = 0,
    val seconds: Int = 0,
    val milliSeconds: Int = 0,
) {

    fun toTime(): String {
        val args = buildList {
            if (hours != 0) add(hours)
            add(minutes)
            add(seconds)
            add(milliSeconds / 10)
        }
        return if (hours == 0)
            "%02d:%02d.%02d".format(*args.toTypedArray())
        else
            "%02d:%02d:%02d.%02d".format(*args.toTypedArray())
    }

    fun increaseSec(): Stopwatch {
        return fromMillis(toMillis() + 10L)
    }

    fun difference(second: Stopwatch): Stopwatch {
        val totalMillis = toMillis() - second.toMillis()
        return fromMillis(totalMillis)
    }

    fun Stopwatch.toMillis() =
        ((hours * 3600 + minutes * 60 + seconds) * 1000 + milliSeconds).toLong()

    fun fromMillis(millis: Long): Stopwatch {
        val totalSeconds = millis / 1000
        return copy(
            hours = (totalSeconds / 3600).toInt(),
            minutes = ((totalSeconds % 3600) / 60).toInt(),
            seconds = (totalSeconds % 60).toInt(),
            milliSeconds = (millis % 1000).toInt(),
        )
    }
}