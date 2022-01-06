package com.vrjoseluis.baseproject.data.local.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "User")
data class UserDbo(
    @PrimaryKey
    val id: Int,
    val name: String,
    val birthdate: Date?,
    val lastRefreshed: Date = Date(1)
)