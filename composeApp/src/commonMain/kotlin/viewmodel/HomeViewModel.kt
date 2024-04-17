package viewmodel

import androidx.compose.runtime.mutableStateOf
import dao.Manga
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class HomeViewModel: ViewModel(){
    
    val BASE_URL = "https://api.mangadex.org/"
    
    private var _manga = mutableStateOf<Manga>(Manga())
    val manga = _manga.value
    private val client = HttpClient(OkHttp){
        Content
        expectSuccess = true
    }
    
    fun getManga(title: String) {
        viewModelScope.launch {
            val response = client.get("$BASE_URL/manga?title=$title")
            if(response.status == HttpStatusCode.OK){
                _manga = response.body()
            }
        }
    }
    
    
    
}