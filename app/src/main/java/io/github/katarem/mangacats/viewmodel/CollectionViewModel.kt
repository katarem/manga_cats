package io.github.katarem.mangacats.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import io.github.katarem.mangacats.api.Retrofit
import io.github.katarem.mangacats.dto.auth.Manga
import io.github.katarem.mangacats.dto.auth.User
import io.github.katarem.mangacats.nav.Routes
import io.github.katarem.mangacats.utils.Status
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CollectionViewModel : ViewModel() {

    private val service = Retrofit.mangaService

    private lateinit var user: User

    private var _suscribedMangas = MutableStateFlow(mutableListOf<Manga>())
    val suscribedMangas = _suscribedMangas.asStateFlow()

    private var _recentMangas = MutableStateFlow(mutableListOf<Manga>())
    val recentMangas = _recentMangas.asStateFlow()

    private var _status = MutableStateFlow(Status.IDLE)
    val status = _status.asStateFlow()

    private var _selectedManga = MutableStateFlow(null as Manga?)
    val selectedManga = _selectedManga.asStateFlow()

    private var _statusMessage = MutableStateFlow("")
    val statusMessage = _statusMessage.asStateFlow()

    fun setUser(user: User){
        this.user = user
    }

    fun setSelectedManga(manga: Manga): Deferred<Unit>{
        return viewModelScope.async {
            return@async _selectedManga.update { manga }
        }
    }


    fun fetchSuscribedMangas(){
        try{
            if(_status.value != Status.LOADING){
                viewModelScope.launch {
                    _status.update { Status.LOADING }
                    Log.d("fetchManga","fetching suscribed mangas de ${user.username}")
                    val response = service.getUserSuscribedMangas(user.username)
                    when(response.code()){
                        200 -> {
                            _suscribedMangas.update { response.body()!!.toMutableList() }
                            _status.update { Status.SUCCESS }
                            Log.d("fetchManga","mangas fetcheados correctamente")
                        }
                        else -> {
                            Log.d("fetchManga","error fetching: ${response.code()}")
                            _status.update { Status.ERROR }
                        }
                    }
                }
            }
        } catch (ex: Exception){
            Log.d("connection","no hay conexion a la api spring")
        }
    }

    fun fetchRecentMangas(){
        try {
            if(_status.value != Status.LOADING){
                viewModelScope.launch {
                    _status.update { Status.LOADING }
                    Log.d("fetchManga","fetching recent mangas de ${user.username}")
                    val response = service.getUserRecentMangas(user.username)
                    when (response.code()){
                        200 -> {
                            _suscribedMangas.update { response.body()!!.toMutableList() }
                            _status.update { Status.SUCCESS }
                            Log.d("fetchManga","mangas fetcheados correctamente")
                        }
                        else -> {
                            Log.d("fetchManga","error fetching: ${response.code()}")
                            _status.update { Status.ERROR }
                        }
                    }
                }
            }
        } catch (ex: Exception){
            Log.d("connection","no hay conexion a la api spring")
        }
    }

    fun updateSuscribedMangas(){
        if(_status.value != Status.LOADING){
            viewModelScope.launch {
                _status.update { Status.LOADING }
                val response = service.updateUserSuscribedMangas(user.username,_suscribedMangas.value)
                when(response.code()){
                    200 -> {
                        _status.update { Status.SUCCESS }
                        Log.d("updateMangas","liked actualizados con exito!")
                    }
                    else -> {
                        _status.update { Status.ERROR }
                        _statusMessage.update { response.body()!!.message }
                    }
                }
            }
        }
    }

    fun updateRecentMangas() {
        if (_status.value != Status.LOADING) {
            viewModelScope.launch {
                _status.update { Status.LOADING }
                val response = service.updateUserRecentMangas(user.username, _suscribedMangas.value)
                when (response.code()) {
                    200 -> {
                        _status.update { Status.SUCCESS }
                        Log.d("updateMangas", "recientes actualizados con exito!")
                    }

                    else -> {
                        _status.update { Status.ERROR }
                        _statusMessage.update { response.body()!!.message }
                    }
                }
            }
        }
    }

    fun fetchMangatoRead(search: SearchViewModel, navController: NavController){
        viewModelScope.launch {
//            try{
//                Log.d("fetchCOLLECTION","EMPIEZO A OBTENER EL MANGA")
//                val mangaObtenido = search.getManga(_selectedManga.value!!.id).await()
//                Log.d("fetchCOLLECTION","TERMINO DE OBTENER EL MANGA")
//                Log.d("fetchCOLLECTION","MANGA= $mangaObtenido")
//                Log.d("fetchCOLLECTION","EMPIEZO A OBTENER LOS CAPITULOS")
//                val capitulos = search.getChapters(_selectedManga.value!!.id)?.await()
//                Log.d("fetchCOLLECTION","TERMINO DE OBTENER LOS CAPITULOS")
//                Log.d("fetchCOLLECTION","CAPS= $capitulos")
//                navController.navigate("${Routes.READER}/${_selectedManga.value!!.currentChapter-1}")
//            } catch (ex: Exception){
//                Log.d("fetchCOLLECTION","$ex")
//            }
        }
    }


}