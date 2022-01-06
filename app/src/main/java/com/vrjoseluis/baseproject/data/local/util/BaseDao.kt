package com.vrjoseluis.baseproject.data.local.util

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

abstract class BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insert(items: List<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insert(item: T)

    @Delete
    protected abstract suspend fun delete(item: T)

    @Delete
    protected abstract suspend fun delete(items: List<T>)

}
