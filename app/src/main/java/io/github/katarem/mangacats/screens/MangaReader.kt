package io.github.katarem.mangacats.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.Coil
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.imageLoader
import coil.request.ImageRequest
import coil.request.ImageResult
import coil.size.Scale
import io.github.katarem.mangacats.R
import io.github.katarem.mangacats.nav.Routes
import io.github.katarem.mangacats.utils.Status
import io.github.katarem.mangacats.viewmodel.ReaderViewModel
import kotlinx.coroutines.Dispatchers

@Composable
fun MangaReader(navController: NavController?, readerViewModel: ReaderViewModel){
    val isCascade = readerViewModel.isCascade
    val chapterIndex = readerViewModel.chapterIndex.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Box(
            modifier = Modifier
                .weight(0.15f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            Text(text = "Chapter ${chapterIndex.value+1}",fontSize = 25.sp, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
        }
            if(isCascade)
                ReaderByCascade(readerViewModel = readerViewModel,modifier = Modifier.weight(3f))
            else
                ReaderByPage(readerViewModel = readerViewModel, modifier = Modifier.weight(3f))
    }
    BackHandler(true) {
        navController?.popBackStack(route = Routes.MANGADETAILS, inclusive = false, saveState = false)
    }

}


@Composable
fun ReaderByPage(readerViewModel: ReaderViewModel, modifier: Modifier){
    val page = readerViewModel.currentPage.collectAsState()
    Column(
        modifier = modifier
    ) {
        SubcomposeAsyncImage(
            model = page.value,
            loading = {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            },
            contentDescription = "", modifier = Modifier
                .weight(3f)
                .fillMaxWidth(), contentScale = ContentScale.FillWidth)
        ReaderControls(readerViewModel = readerViewModel, modifier = Modifier
            .weight(0.3f)
            .fillMaxWidth())
    }
}



@Composable
fun ReaderByCascade(readerViewModel: ReaderViewModel, modifier: Modifier){
    val pages = readerViewModel.getAllPages()
    val apiStatus = readerViewModel.status.collectAsState()
    Column(modifier = modifier) {
        if(apiStatus.value == Status.LOADING)
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(3f)
                    .padding(150.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
            else LazyColumn(modifier = Modifier
            .fillMaxSize()
            .weight(3f), horizontalAlignment = Alignment.CenterHorizontally){
                items(pages){
                    SubcomposeAsyncImage(model = it,
                        loading = {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.secondary,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            )
                        },
                        contentDescription = "", modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.FillWidth)
                }
            }
        ReaderControls(readerViewModel = readerViewModel, modifier = Modifier
            .weight(0.3f)
            .fillMaxWidth())
    }



}

@OptIn(ExperimentalCoilApi::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun ReaderControls(readerViewModel: ReaderViewModel, modifier: Modifier){
    val isCascade = readerViewModel.isCascade
    val pageIndex = readerViewModel.pageIndex.collectAsState()
    val chapterIndex = readerViewModel.chapterIndex.collectAsState()
    val context = LocalContext.current
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(id = R.drawable.double_arrow_left), contentDescription = null, modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clickable {
                    context.imageLoader.memoryCache?.clear()
                    readerViewModel.setChapterIndex(chapterIndex.value - 1)
                })
            if(!isCascade){
                Icon(painter = painterResource(id = R.drawable.arrow_left), contentDescription = null, modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable { readerViewModel.pageBackward() })

                Text(text = "${pageIndex.value+1}",fontSize = 25.sp, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Icon(painter = painterResource(id = R.drawable.arrow_right), contentDescription = null,  modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable { readerViewModel.pageForward() })
            }
            Icon(painter = painterResource(id = R.drawable.double_arrow_right), contentDescription = null, modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clickable {
                    context.imageLoader.diskCache?.clear()
                    context.imageLoader.memoryCache?.clear()
                    readerViewModel.setChapterIndex(chapterIndex.value + 1)
                }
            )
        }
}