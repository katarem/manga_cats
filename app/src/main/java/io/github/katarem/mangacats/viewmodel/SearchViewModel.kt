package io.github.katarem.mangacats.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.katarem.mangacats.api.Retrofit
import io.github.katarem.mangacats.dto.MangaDAO
import io.github.katarem.mangacats.dto.chapter.ChapterDTO
import io.github.katarem.mangacats.utils.SETTINGS
import io.github.katarem.mangacats.utils.Status
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel:ViewModel() {

    private val service = Retrofit.mangadexService


    private var _chapters = MutableStateFlow(listOf<ChapterDTO>())
    val chapters = _chapters.asStateFlow()

    private var _status = MutableStateFlow(Status.LOADING)
    val status = _status.asStateFlow()

    private var _manga = MutableStateFlow(null as MangaDAO?)
    val manga = _manga.asStateFlow()

    private var _searchMangas = MutableStateFlow(mutableListOf<MangaDAO>())
    val searchMangas = _searchMangas.asStateFlow()

    fun setSelectedManga(manga: MangaDAO){
        _manga.update { manga }
    }

//    fun getManga(title: String){
//        viewModelScope.launch {
//            val response = service.getMangaByTitle(title = title)
//            _manga.update { response.getManga(response.data[0]) }
//        }
//    }
//
    fun getManga(id: String): Deferred<MangaDAO>{
        return viewModelScope.async {
            val response = service.getManga(id)
            Log.d("fetchCOLLECTION","manga from api= $response")
            _manga.update { response.getManga() }
            return@async _manga.value!!
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
            } catch (ex: Exception) {
                Log.d("error", "sucedio un error")
            }
        }
    }

    fun searchMangas(title: String){
        viewModelScope.launch {
            try{
                _status.value = Status.LOADING
                val response = service.getMangaByTitle(title = title, lang = listOf(SETTINGS.getMangaLang()))
                Log.d("searchMangas","$response")
                _searchMangas.update { response.getMangaList() }
                _status.value = Status.SUCCESS
            } catch(ex: Exception){
                ex.printStackTrace()
                Log.d("searchMangas","$ex")
            }
        }
    }
}
