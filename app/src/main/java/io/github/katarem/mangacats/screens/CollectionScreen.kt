package io.github.katarem.mangacats.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import io.github.katarem.mangacats.dao.LocalManga
import io.github.katarem.mangacats.nav.Routes
import io.github.katarem.mangacats.utils.SETTINGS
import io.github.katarem.mangacats.viewmodel.CollectionViewModel
import io.github.katarem.mangacats.viewmodel.SearchViewModel
import kotlinx.coroutines.launch

typealias LambdaManga = (LocalManga) -> Unit

@Composable
fun CollectionScreen(navController: NavController?, vm: CollectionViewModel, search: SearchViewModel){
    val coroutineScope = rememberCoroutineScope()
    val suscribedMangas = vm.suscribedMangas.collectAsState()
    val recentMangas = vm.recentMangas.collectAsState()

    val selectedLang = SETTINGS.getMangaLang()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ){
        Text(text = "Your Recent Mangas", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            LazyRow{
                items(recentMangas.value.asReversed()){
                    MangaItem(modifier = Modifier.weight(1f),manga = it){recent ->
                        coroutineScope.launch {
                            search.getManga(recent.uuid).await()
                            search.getChaptersAsync(recent.uuid).await()
                            navController?.navigate("${Routes.READER}/${recent.currentChapter}")
                        }
                    }
                }
            }
        if(recentMangas.value.isEmpty())
            Text(text = "You have no recent mangas yet :(", modifier = Modifier.padding(10.dp))
        Text(text = "Your Liked Mangas", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            LazyRow{
                items(suscribedMangas.value.asReversed()){
                    MangaItem(modifier = Modifier.weight(1f),manga = it){suscribed ->
                        coroutineScope.launch {
                            search.getManga(suscribed.uuid).await()
                            search.getChaptersAsync(suscribed.uuid).await()
                            navController?.navigate("${Routes.READER}/${suscribed.currentChapter}")
                        }
                    }
                }
            }
        if(suscribedMangas.value.isEmpty())
            Text(text = "You have no liked mangas yet :(", modifier = Modifier.padding(10.dp))
    }
}


@Composable
fun MangaItem(modifier: Modifier, manga: LocalManga, onClick: LambdaManga){
    Column(
        modifier = modifier
            .padding(10.dp)
            .clickable { onClick(manga) },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Log.d("cover", "${manga.cover_art}")
        AsyncImage(model = manga.cover_art, contentDescription = "", modifier = Modifier
            .height(240.dp)
            .width(180.dp)
            .background(Color.Blue),
            contentScale = ContentScale.FillBounds
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val title = if(manga.title.length > 20) manga.title.substring(0 until 20) else manga.title
            Text(text = title, overflow = TextOverflow.Ellipsis, maxLines = 2)
            Text(text = "Ch. ${manga.currentChapter+1}")
        }
    }
}
