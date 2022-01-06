package com.vrjoseluis.baseproject.data.model

import java.util.*
import java.util.concurrent.TimeUnit

data class User(
    val id: Int?,
    val name: String,
    val birthdate: Date?,
    val lastRefreshed: Date = Date(1)
){
    fun haveToRefreshFromNetwork(): Boolean = TimeUnit.MILLISECONDS.toMinutes(Date().time - lastRefreshed.time) >= 1
}