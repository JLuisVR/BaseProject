package com.vrjoseluis.baseproject.util

import android.content.Context
import android.widget.DatePicker
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(format:String): Date?{
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    return dateFormat.parse(this)
}

fun Date.toString(format:String): String?{
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    return dateFormat.format(this)
}

fun Context.showToast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun DatePicker.setDate(date: Date?) {
    date?.let {
        val calendar = Calendar.getInstance()
        calendar.time = it
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]
        this.updateDate(year, month, day)
    }
}

fun DatePicker.getDate():Date {
    val calendar = Calendar.getInstance()
    calendar[Calendar.YEAR] = this.year
    calendar[Calendar.MONTH] = this.month
    calendar[Calendar.DAY_OF_MONTH] = this.dayOfMonth
    return calendar.time
}