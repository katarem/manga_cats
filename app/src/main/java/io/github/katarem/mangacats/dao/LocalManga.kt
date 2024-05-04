package io.github.katarem.mangacats.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class LocalManga(
    @PrimaryKey val uuid: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "cover_art") val cover_art: String,
    @ColumnInfo(name = "suscribed") val suscribed: Boolean,
    @ColumnInfo(name = "currentChapter") val currentChapter: Int
)

