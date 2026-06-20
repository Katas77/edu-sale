package com.example.common.util

import java.text.SimpleDateFormat
import java.util.Locale

object DateMapper {
    private const val INPUT_FORMAT = "yyyy-MM-dd"
    private const val OUTPUT_FORMAT = "d MMMM yyyy"

    fun toMillis(dateString: String): Long = try {
        SimpleDateFormat(INPUT_FORMAT, Locale.getDefault()).parse(dateString)?.time ?: 0L
    } catch (e: Exception) {
        0L
    }

    fun toReadableFormat(dateString: String): String = try {
        val parsed = SimpleDateFormat(INPUT_FORMAT, Locale.getDefault()).parse(dateString)
            ?: return dateString
        SimpleDateFormat(OUTPUT_FORMAT, Locale("ru")).format(parsed)
    } catch (e: Exception) {
        dateString
    }
}