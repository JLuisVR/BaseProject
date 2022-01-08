package com.vrjoseluis.baseproject.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vrjoseluis.baseproject.data.local.dbo.UserDbo

@Dao
abstract class UserDao {

    @Query("SELECT * FROM User ORDER BY id DESC")
    abstract suspend fun getAllUser(): List<UserDbo>

    @Query("SELECT * FROM User WHERE id = :id LIMIT 1")
    abstract suspend fun getUserById(id: Int): UserDbo?

    @Query("SELECT * FROM User WHERE name LIKE :name ORDER BY name")
    abstract suspend fun getUserListByName(name: String): List<UserDbo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(items: List<UserDbo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(item: UserDbo)

    @Query("DELETE FROM User")
    abstract suspend fun removeAll()

}