package io.github.katarem.mangacats.dao.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalManga(
    @PrimaryKey val uuid: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "currentChapter") val currentChapter: Int
)

