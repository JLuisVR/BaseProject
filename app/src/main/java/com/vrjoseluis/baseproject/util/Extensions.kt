package com.vrjoseluis.baseproject.util

import android.content.Context
import android.widget.Toast
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(format: String): Date? {
    return try {
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        dateFormat.parse(this)
    } catch (e: ParseException) {
        null
    }
}

fun Date.toString(format: String): String? {
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    return dateFormat.format(this)
}

fun Context.showToast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}