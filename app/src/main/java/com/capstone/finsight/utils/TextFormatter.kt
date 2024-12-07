package com.capstone.finsight.utils

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale
import java.util.concurrent.TimeUnit

object TextFormatter {

    fun getDateTime(seconds: Long, nanoseconds: Int) : String{
        val timestamp = Instant.ofEpochSecond(seconds, nanoseconds.toLong())
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val formattedTime = timestamp.format(formatter)

        return "Posted on $formattedTime"
    }

    fun parseTime(time : String) : String{
        val inputFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.ENGLISH)
        val date = inputFormat.parse(time)

        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

        return outputFormat.format(date?: "")
    }

    fun formatNumber(number: Int): String {
        return when {
            number >= 1_000_000 -> "${number / 1_000_000}M"
            number >= 10_000 -> "${number / 1_000}k"
            else -> number.toString()
        }
    }

    fun getPostInterval(seconds: Long, nanoseconds: Int) : String{
        val postTime = Instant.ofEpochSecond(seconds, nanoseconds.toLong())
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
        val now = LocalDateTime.now()

        val duration = ChronoUnit.SECONDS.between(postTime, now)
        val minutes = TimeUnit.SECONDS.toMinutes(duration)
        val hours = TimeUnit.SECONDS.toHours(duration)
        val days = TimeUnit.SECONDS.toDays(duration)

        return when {
            minutes < 60 -> "$minutes minutes ago"
            hours < 24 -> "$hours hours ago"
            else -> "$days days ago"
        }
    }

}