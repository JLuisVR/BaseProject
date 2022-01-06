package com.vrjoseluis.baseproject.data.remote

import com.vrjoseluis.baseproject.data.model.User
import com.vrjoseluis.baseproject.data.remote.dto.UserDto
import com.vrjoseluis.baseproject.util.toDate
import com.vrjoseluis.baseproject.util.toString
import java.util.*

internal fun UserDto.toBo() = User(
    id,
    name.orEmpty(),
    birthdate?.toDate("yyyy-MM-dd"),
    Date()
)

internal fun User.toDto() = UserDto(
    id,
    name,
    birthdate?.toString("yyyy-MM-dd")
)