package io.github.katarem.mangacats.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import io.github.katarem.mangacats.R
import io.github.katarem.mangacats.dto.auth.Manga
import io.github.katarem.mangacats.nav.Routes
import io.github.katarem.mangacats.viewmodel.CollectionViewModel
import io.github.katarem.mangacats.viewmodel.SearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

typealias LambdaManga = (Manga) -> Unit

@Composable
fun CollectionScreen(navController: NavController?, vm: CollectionViewModel, search: SearchViewModel){
    val coroutineScope = rememberCoroutineScope()
    val suscribedMangas = vm.suscribedMangas.collectAsState()
    val recentMangas = vm.recentMangas.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ){
        Text(text = "Your Recent Mangas", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        if(recentMangas.value.size > 0)
            LazyRow{
                items(recentMangas.value){
                    MangaItem(manga = it){recent ->
                        coroutineScope.launch {
                            vm.setSelectedManga(recent).await()
                            vm.fetchMangatoRead(search, navController!!)
                        }
                    }
                }
            }
        else
            Text(text = "You have no recent mangas yet :(", modifier = Modifier.padding(10.dp))
        Text(text = "Your Liked Mangas", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        if(suscribedMangas.value.size > 0)
            LazyRow{
                items(suscribedMangas.value){
                    MangaItem(manga = it){recent ->
                        coroutineScope.launch {
                            vm.setSelectedManga(recent).await()
                            vm.fetchMangatoRead(search, navController!!)
                        }
                    }
                }
            }
        else
            Text(text = "You have no liked mangas yet :(", modifier = Modifier.padding(10.dp))
    }
}


@Composable
fun MangaItem(manga: Manga, onClick: LambdaManga){
    Column(
        modifier = Modifier
            .padding(10.dp)
            .clickable { onClick(manga) },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(model = manga.cover_art, contentDescription = "", modifier = Modifier
            .height(240.dp)
            .background(Color.Blue),
            contentScale = ContentScale.Inside
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = manga.title)
            Text(text = "Ch. ${manga.currentChapter}")
        }
    }
}
