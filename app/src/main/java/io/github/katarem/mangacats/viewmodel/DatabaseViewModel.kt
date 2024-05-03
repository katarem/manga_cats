package io.github.katarem.mangacats.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import androidx.room.RoomDatabase
import io.github.katarem.mangacats.dao.local.LocalDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DatabaseViewModel() : ViewModel() {

    private lateinit var db: LocalDatabase

    fun setDB(db: LocalDatabase){
        this.db = db
    }

    fun helloWorld(){
        Log.d("db-prueba","ESTADO DB = ${db.isOpen}")
        Log.d("db-prueba","usuarios = ${db.userDao().getAll()}")
    }



}