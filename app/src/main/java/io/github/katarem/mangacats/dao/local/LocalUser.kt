package io.github.katarem.mangacats.dao.local

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalUser(
    @PrimaryKey val uuid: String,
    val username: String,
    val password: String,
    val profilePhoto: String
)
