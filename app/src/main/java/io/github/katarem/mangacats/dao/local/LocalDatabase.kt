package io.github.katarem.mangacats.dao.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalUser::class, LocalManga::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}