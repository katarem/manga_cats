package io.github.katarem.mangacats.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import io.github.katarem.mangacats.components.DisplayButton
import io.github.katarem.mangacats.dto.MangaDAO
import io.github.katarem.mangacats.dto.chapter.ChapterDTO
import io.github.katarem.mangacats.nav.Routes
import io.github.katarem.mangacats.utils.Status
import io.github.katarem.mangacats.viewmodel.SearchViewModel

typealias LambdaChapter = (Int) -> Unit

@Composable
fun MangaDetails(navController: NavController?, manga: MangaDAO?, viewModel: SearchViewModel) {
    val scrollState = rememberScrollState()
    manga?.let {
        LaunchedEffect(Unit){
            viewModel.getChapters(manga.id)
        }
        val estado = viewModel.status.collectAsState()
        val chapters = viewModel.chapters.collectAsState()
        val selectedChapter = remember{ mutableIntStateOf(-1) }
        if(selectedChapter.intValue != -1) {
            Log.d("toReader","voy al capitulo ${selectedChapter.intValue}")
            navController?.navigate("${Routes.READER}/${selectedChapter.intValue}")
        }

        Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .scrollable(state = scrollState, orientation = Orientation.Vertical),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = manga.cover ?: "",
            contentDescription = "Manga cover",
            modifier = Modifier.weight(1f)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = manga.title,
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            DescriptionToggle(description = manga.description)
            Log.d("MangaDetails","estado: ${estado.value}")
            if(estado.value == Status.SUCCESS)
                ChaptersToggle(chapters = chapters.value) {
                    selectedChapter.intValue = it
                }
        }

    } } ?: Text("No hay manga seleccionado")
}

@Composable
fun ChaptersToggle(chapters: List<ChapterDTO>, onSelect: LambdaChapter){
    val chaptersComponent = @Composable() {
        if(chapters.isEmpty()) Text(text = "There aren't chapters in your selected language :(")
        LazyColumn( modifier = Modifier.fillMaxWidth()){
            itemsIndexed(chapters){index,chapter ->
                ChapterItem(chapter = chapter, index = index, onSelect)
            }
        }
    }
    DisplayButton(
        onDisplay = chaptersComponent,
        title = "Chapters"
    )
}

@Composable
fun ChapterItem(chapter: ChapterDTO, index: Int, onSelect: LambdaChapter){
    val title = (chapter.attributes?.title?:"[NO TITLE]").ifEmpty { "[NO TITLE]" }
    Log.d("chapterItem","index=$index")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                Log.d("chapterItem", "he pulsado el capitulo $index")
                onSelect(index)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "${index+1}")
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = title, modifier = Modifier.weight(1f), overflow = TextOverflow.Clip)
    }
}





@Composable
fun DescriptionToggle(description: String) {

    val summaryComponent = @Composable() {
        val summaryList = description.split(".")

        val summary = if(summaryList.size > 2)
            summaryList.subList(0, 3).toString()
            .replace("Description(en=", "")
            .replace("[", "")
            .replace("]", "") + "."
        else summaryList.subList(0,summaryList.size).toString()
            .replace("Description(en=", "")
            .replace("[", "")
            .replace("]", "") + "."
        Text(
            text = summary,
            textAlign = TextAlign.Justify,
        )
    }
    DisplayButton(
        onDisplay = summaryComponent,
        title = "Summary"
    )
}