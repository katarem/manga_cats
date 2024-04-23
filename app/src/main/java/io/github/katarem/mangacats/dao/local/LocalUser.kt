package io.github.katarem.mangacats.dao.local

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalUser(
    @PrimaryKey val uuid: String,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "profile_photo") val profilePhoto: String,
    @Embedded val suscribedMangas: List<LocalManga>,
    @Embedded val recentMangas: List<LocalManga>
)
