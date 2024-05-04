package io.github.katarem.mangacats.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LocalManga::class], version = 2)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun mangaDao(): MangaDao

    companion object{

        lateinit var instance: LocalDatabase

        fun createInstance(context: Context){
            instance = Room.databaseBuilder(context, LocalDatabase::class.java, "mangacats_local")
                .fallbackToDestructiveMigration().build()
        }

    }
}