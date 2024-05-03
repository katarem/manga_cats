package io.github.katarem.mangacats.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import io.github.katarem.mangacats.components.CustomTextField
import io.github.katarem.mangacats.components.DisplayButton
import io.github.katarem.mangacats.nav.Routes
import io.github.katarem.mangacats.utils.CURRENT_VERSION
import io.github.katarem.mangacats.utils.SETTINGS
import io.github.katarem.mangacats.utils.Status
import io.github.katarem.mangacats.utils.profileDefault
import io.github.katarem.mangacats.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(navController: NavController?, settingsViewModel: SettingsViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        MangaLanguageSelector()
        ReadingModeSelector()
        Text(text = "Version $CURRENT_VERSION")

        Button(onClick = { settingsViewModel.deleteRecentMangas() }) {
            Text(text = "Borrar Mangas Recientes")
        }
        Button(onClick = { settingsViewModel.deleteSuscribedMangas() }) {
            Text(text = "Borrar Mangas Suscritos")
        }
        Button(onClick = { settingsViewModel.deleteAllMangas() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red
            )) {
            Text(text = "Borrar TODOS los mangas")
        }
    }
}

@Composable
fun MangaLanguageSelector() {
    val mangaLang = remember { mutableStateOf(SETTINGS.getMangaLang()) }
    DisplayButton(
        title = "Manga Language: ${mangaLang.value}"
    ) {
        LazyColumn {
            items(Languages.entries) {
                Text(text = it.name,fontSize = 17.sp,fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp).clickable {
                    setLanguage(it)
                    mangaLang.value = SETTINGS.getMangaLang()
                })
            }
        }
    }
}


@Composable
fun ReadingModeSelector() {
    val readingMode = remember { mutableStateOf(SETTINGS.getReadingMode()) }
    DisplayButton(
        title = "Reading Mode: ${readingMode.value}"
    ) {
        LazyColumn {
            items(ReadingMode.entries) {
                Text(text = it.name, fontSize = 15.sp,fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp).clickable {
                    setReadingMode(it)
                    readingMode.value = SETTINGS.getReadingMode()
                })
            }
        }
    }
}


private fun setReadingMode(mode: ReadingMode) {
    val selectedReadingMode = when (mode) {
        ReadingMode.PAGE -> "page"
        ReadingMode.CASCADE -> "cascade"
    }
    SETTINGS.setReadingMode(selectedReadingMode)
}


private fun setLanguage(lang: Languages) {
    SETTINGS.setMangaLang(lang.value)
}

private enum class ReadingMode {
    PAGE,
    CASCADE
}

private enum class Languages(val value: String) {
    SPANISH("es"),
    AMERICAN_ENGLISH("en"),
    UK_ENGLISH("uk"),
    AMERCIAN_SPANISH("es-la"),
//    FRENCH("fr"),
//    PORTUGUESE("pt-br"),
//    RUSSIAN("ru"),
//    DEUTSCH("de"),
//    ITALIAN("it")
}