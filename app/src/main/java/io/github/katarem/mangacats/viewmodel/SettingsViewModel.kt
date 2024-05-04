package io.github.katarem.mangacats.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.katarem.mangacats.dao.LocalDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel(){

    val mangaService = LocalDatabase.instance.mangaDao()

    val deleteAllMangas = {
        viewModelScope.launch(Dispatchers.IO) {
            mangaService.deleteAllMangas()
        }
    }

    val deleteSuscribedMangas = {
        viewModelScope.launch(Dispatchers.IO) {
            mangaService.deleteAllSuscribedMangas()
        }
    }

    val deleteRecentMangas = {
        viewModelScope.launch(Dispatchers.IO) {
            mangaService.deleteAllRecentMangas()
        }
    }

}