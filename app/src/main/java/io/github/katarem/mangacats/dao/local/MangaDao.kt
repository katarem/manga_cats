package io.github.katarem.mangacats.dao.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert

@Dao
interface MangaDao {
    @Query("SELECT * FROM LocalManga")
    fun getAll(): List<LocalManga>
    @Query("SELECT * FROM LocalManga WHERE suscribed = 1")
    suspend fun getSuscribedMangas(): List<LocalManga>

    @Query("SELECT * FROM LocalManga WHERE suscribed = 0")
    suspend fun getRecentMangas(): List<LocalManga>
    @Upsert
    suspend fun updateManga(manga: LocalManga)
    @Delete
    suspend fun deleteManga(manga: LocalManga)

    @Query("DELETE FROM LocalManga")
    suspend fun deleteAllMangas()

    @Query("DELETE FROM LocalManga WHERE suscribed = 1")
    suspend fun deleteAllSuscribedMangas()

    @Query("DELETE FROM LocalManga WHERE suscribed = 0")
    suspend fun deleteAllRecentMangas()

}