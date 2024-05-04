package io.github.katarem.mangacats.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.katarem.mangacats.dao.LocalDatabase
import io.github.katarem.mangacats.dao.LocalManga
import kotlinx.coroutines.Dispatchers
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