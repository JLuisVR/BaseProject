package com.vrjoseluis.baseproject.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vrjoseluis.baseproject.data.local.converter.DateConverter
import com.vrjoseluis.baseproject.data.local.dbo.UserDbo

@Database(entities = [UserDbo::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private const val DATABASE_NAME = "RoomDatabase.db"

        fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppRoomDatabase::class.java,
                DATABASE_NAME
            ).build()
    }
}
