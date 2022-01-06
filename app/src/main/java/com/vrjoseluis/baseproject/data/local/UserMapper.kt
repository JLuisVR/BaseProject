package com.vrjoseluis.baseproject.data.local

import com.vrjoseluis.baseproject.data.local.dbo.UserDbo
import com.vrjoseluis.baseproject.data.model.User

internal fun UserDbo.toBo() = User(
    id,
    name,
    birthdate,
    lastRefreshed
)

internal fun User.toDbo() = UserDbo(
    id?:0,
    name,
    birthdate,
    lastRefreshed
)