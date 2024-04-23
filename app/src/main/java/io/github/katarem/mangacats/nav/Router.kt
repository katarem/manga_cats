package io.github.katarem.mangacats.nav

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import io.github.katarem.mangacats.api.Retrofit
import io.github.katarem.mangacats.components.BottomBar
import io.github.katarem.mangacats.dao.local.LocalDatabase
import io.github.katarem.mangacats.dto.MangaDAO
import io.github.katarem.mangacats.screens.CollectionScreen
import io.github.katarem.mangacats.screens.LoginScreen
import io.github.katarem.mangacats.screens.MangaDetails
import io.github.katarem.mangacats.screens.MangaReader
import io.github.katarem.mangacats.screens.SearchScreen
import io.github.katarem.mangacats.screens.SettingsScreen
import io.github.katarem.mangacats.utils.SETTINGS
import io.github.katarem.mangacats.utils.Status
import io.github.katarem.mangacats.viewmodel.CollectionViewModel
import io.github.katarem.mangacats.viewmodel.CredentialsViewModel
import io.github.katarem.mangacats.viewmodel.SearchViewModel
import io.github.katarem.mangacats.viewmodel.ReaderViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration

@Composable
fun Router(){

    val searchViewModel: SearchViewModel = viewModel()
    val credentialsViewModel: CredentialsViewModel = viewModel()
    val collectionViewModel: CollectionViewModel = viewModel()
    val navController = rememberNavController()
    val readerViewModel: ReaderViewModel = viewModel()

    val db = Room.databaseBuilder(
        LocalContext.current,
        LocalDatabase::class.java, "mangacats_local"
    ).build()


//    LaunchedEffect(Unit){
//        val isLoggedIn = credentialsViewModel.setUser(SETTINGS.getUser())
//        if(isLoggedIn) {
//            async {
//                collectionViewModel.setUser(SETTINGS.getUser()!!)
//                collectionViewModel.fetchSuscribedMangas()
//                collectionViewModel.fetchRecentMangas()
//                credentialsViewModel.setStatus(Status.SUCCESS)
//            }.await()
//        }
//    }

    Scaffold(
        topBar = {},
        bottomBar = { BottomBar( onScreenChange = {
            navController.popBackStack(route = it, inclusive = true, saveState = false)
            navController.navigate(it)
        } )},
    ){
        NavHost(navController = navController, startDestination = Routes.HOMESCREEN, modifier = Modifier.padding(it)){
            composable(Routes.SETTINGS){
                SettingsScreen(navController = navController, credentialsViewModel = credentialsViewModel)
            }
            composable(Routes.HOMESCREEN){
                SearchScreen(navController = navController, viewModel = searchViewModel)
            }
            composable(Routes.COLLECTION){
                CollectionScreen(navController = navController, vm = collectionViewModel,
                    search = searchViewModel)
            }
            composable(Routes.MANGADETAILS){
                val manga = searchViewModel.manga.collectAsState().value
                Log.d("Router", "Manga: $manga")
                MangaDetails(navController, manga, searchViewModel)
            }
            composable("${Routes.READER}/{index}"){
                val index = it.arguments?.getString("index")?.toInt() ?: 0
                Log.d("toReader","$index")
                val currentManga = searchViewModel.manga.collectAsState().value!!
                Log.d("toReader","$currentManga")
                readerViewModel.setManga(currentManga)
                Log.d("toReader","manga agregado correctamente")
                readerViewModel.setChapterIndex(index)
                MangaReader(navController = navController, readerViewModel = readerViewModel)
            }

            composable(Routes.LOGIN){
                LoginScreen(navController = navController, credentialsViewModel = credentialsViewModel)
            }

        }
    }

}

object Routes{
    val LOGIN = "login"
    val HOMESCREEN = "homescreen"
    val COLLECTION = "collection"
    val MANGADETAILS = "mangadetails"
    val READER = "reader"
    val SETTINGS = "settings"
}