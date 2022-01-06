package com.vrjoseluis.baseproject.data.local.util

import com.vrjoseluis.baseproject.data.local.dbo.UserDbo
import com.vrjoseluis.baseproject.data.model.User

internal fun UserDbo.toBo() = User(
    id,
    name,
    birthdate,
    lastRefreshed
)