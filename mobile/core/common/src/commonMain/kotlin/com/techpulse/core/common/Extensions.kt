package com.techpulse.core.common

import kotlinx.datetime.*

fun String.capitalizeFirst(): String =
    replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

fun String.toSlug(): String =
    lowercase().replace(Regex("[^a-z0-9]+"), "-").trim('-')

fun Long.toReadableDate(): String {
    val instant = Instant.fromEpochMilliseconds(this)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val month = localDateTime.month.name.lowercase().capitalizeFirst().take(3)
    return "$month ${localDateTime.dayOfMonth}, ${localDateTime.year}"
}

fun Int.toOrdinal(): String = when {
    this % 100 in 11..13 -> "${this}th"
    this % 10 == 1 -> "${this}st"
    this % 10 == 2 -> "${this}nd"
    this % 10 == 3 -> "${this}rd"
    else -> "${this}th"
}

fun <T> List<T>.safeSubList(fromIndex: Int, toIndex: Int): List<T> =
    subList(fromIndex.coerceAtLeast(0), toIndex.coerceAtMost(size))

fun Float.toPercentString(): String = "${(this * 100).toInt()}%"

fun String.ellipsize(maxLength: Int): String =
    if (length <= maxLength) this else take(maxLength - 1) + "…"
