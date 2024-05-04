package io.github.katarem.mangacats.nav

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.katarem.mangacats.components.BottomBar
import io.github.katarem.mangacats.dao.LocalDatabase
import io.github.katarem.mangacats.screens.CollectionScreen
import io.github.katarem.mangacats.screens.MangaDetails
import io.github.katarem.mangacats.screens.MangaReader
import io.github.katarem.mangacats.screens.SearchScreen
import io.github.katarem.mangacats.screens.SettingsScreen
import io.github.katarem.mangacats.viewmodel.CollectionViewModel
import io.github.katarem.mangacats.viewmodel.SettingsViewModel
import io.github.katarem.mangacats.viewmodel.SearchViewModel
import io.github.katarem.mangacats.viewmodel.ReaderViewModel
import kotlinx.coroutines.Dispatchers

@Composable
fun Router(){

    val searchViewModel: SearchViewModel = viewModel()
    val settingsViewModel: SettingsViewModel = viewModel()
    val collectionViewModel: CollectionViewModel = viewModel()
    val navController = rememberNavController()
    val readerViewModel: ReaderViewModel = viewModel()
    val mangaService = LocalDatabase.instance.mangaDao()
    Scaffold(
        topBar = {},
        bottomBar = { BottomBar( onScreenChange = {
            navController.popBackStack(route = it, inclusive = true, saveState = false)
            navController.navigate(it)
        } )},
    ){
        NavHost(navController = navController, startDestination = Routes.HOMESCREEN, modifier = Modifier.padding(it)){
            composable(Routes.SETTINGS){
                SettingsScreen(navController = navController, settingsViewModel = settingsViewModel)
            }
            composable(Routes.HOMESCREEN){
                SearchScreen(navController = navController, viewModel = searchViewModel)
            }
            composable(Routes.COLLECTION){
                collectionViewModel.fetchMangas()
                CollectionScreen(navController = navController, vm = collectionViewModel,
                    search = searchViewModel)

            }
            composable(Routes.MANGADETAILS){
                MangaDetails(navController, searchViewModel)
            }
            composable("${Routes.READER}/{index}"){
                val index = it.arguments?.getString("index")?.toInt() ?: 0
                Log.d("toReader","$index")
                val currentManga = searchViewModel.manga.collectAsState().value!!
                Log.d("toReader","$currentManga")
                Log.d("sync","COVER MANGA ROUTER = ${currentManga.cover}")
                readerViewModel.setManga(currentManga)
                Log.d("toReader","manga agregado correctamente")
                readerViewModel.setChapterIndex(index)
                LaunchedEffect(Dispatchers.IO){
                    val isSuscribed = mangaService.getSuscribedMangas().firstOrNull { m -> m.uuid == currentManga.id } != null
                    val localManga = currentManga.toLocalManga(isSuscribed, currentChapter = index)
                    mangaService.updateManga(localManga)
                    Log.d("sync","manga actualizado de capitulo: ${localManga.currentChapter}")
                }
                MangaReader(navController = navController, readerViewModel = readerViewModel)
            }

        }
    }

}

object Routes{
    val HOMESCREEN = "homescreen"
    val COLLECTION = "collection"
    val MANGADETAILS = "mangadetails"
    val READER = "reader"
    val SETTINGS = "settings"
}