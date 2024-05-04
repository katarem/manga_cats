package io.github.katarem.mangacats.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.github.katarem.mangacats.components.LambdaManga
import io.github.katarem.mangacats.components.SearchBar
import io.github.katarem.mangacats.components.SearchList
import io.github.katarem.mangacats.dto.MangaDTO
import io.github.katarem.mangacats.nav.Routes
import io.github.katarem.mangacats.utils.Status
import io.github.katarem.mangacats.viewmodel.SearchViewModel
import kotlinx.coroutines.Dispatchers

@Composable
fun SearchScreen(navController: NavController?, viewModel: SearchViewModel){

    val context = LocalContext.current

    val searchMangas = viewModel.searchMangas.collectAsState()
    val callState = viewModel.status.collectAsState()
    val errorMessage = viewModel.errorMessage.collectAsState()
    val onMangaClick : LambdaManga = { manga: MangaDTO ->
        viewModel.setSelectedManga(manga)
        viewModel.resetStatus()
        navController?.navigate(Routes.MANGADETAILS)
    }

    if(callState.value == Status.ERROR){
        LaunchedEffect(Dispatchers.IO){
            Log.d("toast","me ejecuto search")
            Toast.makeText(context,errorMessage.value, Toast.LENGTH_SHORT)
                .show()
        }
    }


    LaunchedEffect(Unit){
        viewModel.searchMangas(title = "")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        SearchBar{
            viewModel.searchMangas(it)
        }
        if(callState.value == Status.SUCCESS){
            SearchList(mangaList = searchMangas.value, onClick = onMangaClick)
        }
    }
}

