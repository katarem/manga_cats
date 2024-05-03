package io.github.katarem.mangacats.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import io.github.katarem.mangacats.MainActivity
import io.github.katarem.mangacats.api.Retrofit
import io.github.katarem.mangacats.dao.local.LocalDatabase
import io.github.katarem.mangacats.dao.local.LocalManga
import io.github.katarem.mangacats.dto.auth.Manga
import io.github.katarem.mangacats.dto.auth.User
import io.github.katarem.mangacats.nav.Routes
import io.github.katarem.mangacats.utils.Status
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CollectionViewModel : ViewModel() {

    private val service = LocalDatabase.instance.mangaDao()

    private var _suscribedMangas = MutableStateFlow(listOf<LocalManga>())
    private var _recentMangas = MutableStateFlow(listOf<LocalManga>())

    val suscribedMangas = _suscribedMangas.asStateFlow()
    val recentMangas = _recentMangas.asStateFlow()

    init{
        viewModelScope.launch(Dispatchers.IO){
            _suscribedMangas.update { service.getSuscribedMangas() }
            _recentMangas.update { service.getRecentMangas() }
        }
    }

    fun fetchMangas(){
        viewModelScope.launch {
            _recentMangas.update { service.getRecentMangas() }
            _suscribedMangas.update { service.getSuscribedMangas() }
        }
    }

    private fun fetchSuscribedMangas(){
       viewModelScope.launch {
           _suscribedMangas.update { service.getSuscribedMangas() }
       }
    }

    private fun fetchRecentMangas(){
        viewModelScope.launch {
            _suscribedMangas.update { service.getRecentMangas() }
        }
    }

    fun updateManga(manga: LocalManga){
        viewModelScope.launch{
            service.updateManga(manga)
            fetchRecentMangas()
            fetchSuscribedMangas()
        }
    }

    fun removeManga(manga: LocalManga){
        viewModelScope.launch {
            service.deleteManga(manga)
            fetchRecentMangas()
            fetchSuscribedMangas()
        }
    }

}