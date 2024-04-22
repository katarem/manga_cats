package io.github.katarem.mangacats.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.Coil
import coil.ImageLoader
import coil.executeBlocking
import coil.request.ImageRequest
import coil.request.ImageResult
import io.github.katarem.mangacats.api.Retrofit
import io.github.katarem.mangacats.dto.MangaDAO
import io.github.katarem.mangacats.dto.chapter.ChapterDTO
import io.github.katarem.mangacats.dto.chapterpages.ChapterPages
import io.github.katarem.mangacats.utils.SETTINGS
import io.github.katarem.mangacats.utils.Status
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReaderViewModel : ViewModel(){

    private val service = Retrofit.mangadexService
    private val apiService = Retrofit.mangaService
    val isCascade = SETTINGS.getReadingMode() == "cascade"

    val saveData = true

    private var manga = MutableStateFlow(null as MangaDAO?)
    private val user = SETTINGS.getUser()
    private var chapters = MutableStateFlow(mutableListOf<ChapterDTO>())
    private var _baseUrl = MutableStateFlow("")
    private var _chapterPages = MutableStateFlow(ChapterPages())
    val chapterPages = _chapterPages.asStateFlow()

    private var _status = MutableStateFlow(Status.IDLE)
    val status = _status.asStateFlow()
    private var _pageIndex = MutableStateFlow(0)
    val pageIndex = _pageIndex.asStateFlow()

    private var _chapterIndex = MutableStateFlow(0)
    val chapterIndex = _chapterIndex.asStateFlow()

    private var _currentPage = MutableStateFlow("")
    val currentPage = _currentPage.asStateFlow()

    private var _currentChapter = MutableStateFlow("")
    val currentChapter = _currentChapter.asStateFlow()

    fun pageForward(){
        viewModelScope.launch {
            if( _pageIndex.value < _chapterPages.value.data.size){
                _pageIndex.value += 1
                getLink()
            }
            Log.d("getPage","paginaActual = ${_pageIndex.value} MODO ${SETTINGS.getReadingMode()}")
        }
    }

    fun pageBackward(){
        viewModelScope.launch {
            if( _pageIndex.value > 0){
                _pageIndex.value -= 1
                getLink()
            }
            Log.d("getPage","paginaActual = ${_pageIndex.value} MODO ${SETTINGS.getReadingMode()}")
        }
    }

    fun setChapterIndex(newValue: Int){
        if(_status.value != Status.LOADING){
            Log.d("toChapter","$newValue de ${chapters.value.size}")
            if(newValue < chapters.value.size && newValue > -1){
                viewModelScope.launch {
                    _status.update { Status.LOADING }
                    async {
                        _chapterIndex.update { newValue }
                        val response = service.getPagesByChapterId(chapters.value[_chapterIndex.value].id!!)
                        Log.d("toChapter","$response")
                        _chapterPages.update { response.chapterPages!! }
                        _baseUrl.update { response.baseUrl!! }
                        _pageIndex.update { 0 }
                        getLink()
                    }.await()
                    _status.update { Status.SUCCESS }
                }
            }
        }
    }
    fun setManga(mangaDAO: MangaDAO){
        manga.update { mangaDAO }
        chapters.update { mangaDAO.chapters }
    }

    fun getLink(): String{
        _currentPage.update {
            if(saveData)"${_baseUrl.value}/data-saver/${_chapterPages.value.hash}/${_chapterPages.value.dataSaver[_pageIndex.value]}"
            else "${_baseUrl.value}/data/${_chapterPages.value.hash}/${_chapterPages.value.data[_pageIndex.value]}"
        }
        Log.d("loadImage", "pagina cargada")
        return _currentPage.value
    }

    fun getAllPages(): List<String> {
        val deferredResult = viewModelScope.async {
            List(_chapterPages.value.data.size) { index ->
                "${_baseUrl.value}/data-saver/${_chapterPages.value.hash}/${_chapterPages.value.dataSaver[index]}"
            }
        }

        var pages: List<String> = emptyList()

        viewModelScope.launch {
            pages = deferredResult.await()
        }

        return pages
    }


}