package io.github.katarem.mangacats.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.katarem.mangacats.api.Retrofit
import io.github.katarem.mangacats.dao.LocalDatabase
import io.github.katarem.mangacats.dto.MangaDTO
import io.github.katarem.mangacats.dto.chapter.ChapterDTO
import io.github.katarem.mangacats.utils.SETTINGS
import io.github.katarem.mangacats.utils.Status
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel:ViewModel() {

    private val service = Retrofit.mangadexService
    private val localService = LocalDatabase.instance.mangaDao()

    private var _isSuscribed = MutableStateFlow(false)
    val isSuscribed = _isSuscribed.asStateFlow()

    private var _chapters = MutableStateFlow(listOf<ChapterDTO>())
    val chapters = _chapters.asStateFlow()

    private var _status = MutableStateFlow(Status.LOADING)
    val status = _status.asStateFlow()

    private var _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    private var _manga = MutableStateFlow(null as MangaDTO?)
    val manga = _manga.asStateFlow()

    private var _searchMangas = MutableStateFlow(mutableListOf<MangaDTO>())
    val searchMangas = _searchMangas.asStateFlow()

    fun changeSuscribedState(newState: Boolean){
        _isSuscribed.update { newState }
    }

    fun resetStatus(){
        _status.update { Status.IDLE }
    }

    fun setSelectedManga(manga: MangaDTO){
        _manga.update { manga }
        _manga.value?.cover = manga.cover
    }

    fun getManga(id: String): Deferred<Unit> {
        return viewModelScope.async {
            val response = service.getManga(id)
            Log.d("fetchCOLLECTION","manga from api= $response")
            val fileName = response.data.relationships.first{ it.type == "cover_art"}.attributes?.fileName
            _manga.update { response.getManga() }
            _manga.value?.cover = "https://uploads.mangadex.org/covers/${_manga.value?.id}/${fileName}"
            Log.d("sync", "manga de api con cover = ${_manga.value?.id} ${_manga.value?.cover}")
        }
    }

    fun getChaptersAsync(mangaId: String) = viewModelScope.async{
        try {
            _status.update { Status.LOADING }

            val response = service.getChaptersFeed(
                mangaId = mangaId,
                translatedLanguage = SETTINGS.getMangaLang(),
                limit = 1
            )
            Log.d("fetchCOLLECTION", "$response")
            val chaptersList = mutableListOf<ChapterDTO>()
            val total = response.total!!
            var limit = response.limit!!
            val difference = (total - limit) / 2 // we start with offset at 0
            response.data.forEach { chaptersList.add(it) }
            Log.d(
                "fetchCOLLECTION",
                "me quedan capitulos por obtener, voy por el $limit y me quedan $difference"
            )
            if (difference > 0) {
                for (i in 0..2) {
                    val secondResponse = service.getChaptersFeed(
                        mangaId = mangaId,
                        offset = limit,
                        translatedLanguage = SETTINGS.getMangaLang(),
                        limit = difference
                    )
                    Log.d(
                        "getChapters",
                        "me quedan capitulos por obtener, voy por el $limit y me quedan $difference"
                    )
                    Log.d("getChapters", "$secondResponse")
                    secondResponse.data.forEach { chaptersList.add(it) }
                    limit += difference
                }
            }

            _chapters.update { chaptersList }
            _manga.value?.chapters = _chapters.value.toMutableList()
            _status.update { Status.SUCCESS }
            Log.d("getChapters", "capitulos obtenidos!")
        } catch (ex: Exception) {
            Log.d("getChapters", "ERROR: $ex")
            _errorMessage.update { "Ha ocurrido un error en la obtención de capítulos" }
            _status.update { Status.ERROR }
        }
    }

    fun getChapters(mangaId: String) {
        viewModelScope.launch {
            try {
                _status.update { Status.LOADING }

                val response = service.getChaptersFeed(
                    mangaId = mangaId,
                    translatedLanguage = SETTINGS.getMangaLang(),
                    limit = 1
                )
                Log.d("fetchCOLLECTION", "$response")
                val chaptersList = mutableListOf<ChapterDTO>()
                val total = response.total!!
                var limit = response.limit!!
                val difference = (total - limit) / 2 // we start with offset at 0
                response.data.forEach { chaptersList.add(it) }
                Log.d(
                    "fetchCOLLECTION",
                    "me quedan capitulos por obtener, voy por el $limit y me quedan $difference, en total hay $total"
                )
                if (difference > 0) {
                    for (i in 0..2) {
                        val secondResponse = service.getChaptersFeed(
                            mangaId = mangaId,
                            offset = limit,
                            translatedLanguage = SETTINGS.getMangaLang(),
                            limit = difference
                        )
                        Log.d(
                            "getChapters",
                            "me quedan capitulos por obtener, voy por el $limit y me quedan $difference"
                        )
                        Log.d("getChapters", "$secondResponse")
                        secondResponse.data.forEach { chaptersList.add(it) }
                        limit += difference
                    }
                }

                _chapters.update { chaptersList }
                _manga.value?.chapters = _chapters.value.toMutableList()
                _status.update { Status.SUCCESS }
                Log.d("getChapters", "capitulos obtenidos!")
            } catch (ex: Exception) {
                Log.d("error", "sucedio un error: $ex")
                _errorMessage.update { "Ha ocurrido un error en la obtención de capitulos" }
                _status.update { Status.ERROR }
            }
        }
    }

    fun suscribeToManga(){
        viewModelScope.launch(Dispatchers.IO){
            manga.value?.let { localService.updateManga(it.toLocalManga(suscribed = true)) }
        }
    }

    fun unsuscribeToManga(){
        viewModelScope.launch {
            manga.value?.let {
                localService.deleteManga(it.toLocalManga(suscribed = true))
                Log.d("isSuscribed","manga eliminado")
            }
                ?: Log.d("isSuscribed","manga es null")
        }
    }

    fun searchMangas(title: String){
        viewModelScope.launch {
            try{
                _status.update { Status.LOADING }
                val response = service.getMangaByTitle(title = title, lang = listOf(SETTINGS.getMangaLang()))
                //Log.d("searchMangas","$response")
                _searchMangas.update { response.getMangaList() }
                _status.update { Status.SUCCESS }
            } catch(ex: Exception){
                ex.printStackTrace()
                Log.d("searchMangas","$ex")
                _errorMessage.update { "Ha ocurrido un error en la obtención de mangas" }
                _status.update { Status.ERROR }
            }
        }
    }
}
