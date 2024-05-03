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
    @Query("SELECT * FROM LocalManga l WHERE l.suscribed = TRUE")
    suspend fun getSuscribedMangas(): List<LocalManga>

    @Query("SELECT * FROM LocalManga l WHERE l.suscribed = FALSE")
    suspend fun getRecentMangas(): List<LocalManga>
    @Upsert
    suspend fun updateManga(manga: LocalManga)
    @Delete
    suspend fun deleteManga(manga: LocalManga)

    @Query("DELETE FROM LocalManga")
    suspend fun deleteAllMangas()

    @Query("DELETE FROM LocalManga WHERE suscribed = TRUE")
    suspend fun deleteAllSuscribedMangas()

    @Query("DELETE FROM LocalManga WHERE suscribed = FALSE")
    suspend fun deleteAllRecentMangas()

}