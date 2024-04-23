package io.github.katarem.mangacats.dao.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao{
    @Query("SELECT * FROM LocalUser")
    fun getAll(): List<LocalUser>

    @Insert
    fun registerUser(user: LocalUser)


}
