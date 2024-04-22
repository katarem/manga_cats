package io.github.katarem.mangacats.utils

import android.content.Context
import io.github.katarem.mangacats.dto.auth.User

class Settings(context: Context){

    private val storage = context.getSharedPreferences("UserPrefs",0)

    fun clearAccount(){
        storage.edit().putString("username",null).apply()
        storage.edit().putString("password",null).apply()
        storage.edit().putString("profileImg",null).apply()
    }

    fun getUser(): User?{
        val username = storage.getString("username","").toString()
        val password = storage.getString("password","").toString()
        val profileImg = storage.getString("profileImg",null)
        if(username.isEmpty() || password.isEmpty()) return null
        return User(username, password, profileImg)
    }

    fun saveUser(user: User){
        storage.edit().putString("username",user.username).apply()
        storage.edit().putString("password",user.password).apply()
        user.profileImg?.let { storage.edit().putString("profileImg",it).apply() }
    }

    fun getReadingMode(): String{
        return storage.getString("reading_mode","page").toString()
    }

    fun getMangaLang(): String{
        return storage.getString("manga_lang","en").toString()
    }

    fun setReadingMode(mode: String){
        storage.edit().putString("reading_mode",mode).apply()
    }

    fun setMangaLang(lang: String){
        storage.edit().putString("manga_lang",lang).apply()
    }

}