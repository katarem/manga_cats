package io.github.katarem.mangacats.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.github.katarem.mangacats.R
import io.github.katarem.mangacats.utils.CURRENT_VERSION
import io.github.katarem.mangacats.utils.SETTINGS
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
        Button(
            onClick = { settingsViewModel.deleteAllMangas() }, colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red
            )
        ) {
            Text(text = "Borrar TODOS los mangas")
        }
    }
}

@Composable
fun MangaLanguageSelector() {
    var mangaLang by remember { mutableStateOf(Languages.findByValue(SETTINGS.getMangaLang()) ?: Languages.AMERICAN_ENGLISH) }
    var showLangs by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Manga language ")
        Spacer(modifier = Modifier.width(10.dp))
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ), modifier = Modifier.fillMaxWidth()
        ) {
            LazyColumn(
            ) {
                if (showLangs)
                    items(Languages.entries) {
                        LanguageItem(lang = it) {
                            setLanguage(it)
                            mangaLang = Languages.findByValue(SETTINGS.getMangaLang()) ?: Languages.AMERICAN_ENGLISH
                            showLangs = false
                        }
                    }
                else
                    item {
                        LanguageItem(lang = mangaLang) { showLangs = true }
                    }
            }
        }

    }
}


private val LanguageItem: @Composable (lang: Languages, onClick: () -> Unit) -> Unit = {
lang, onClick ->
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }, verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = lang.name.replace('_', ' '),
            fontSize = 11.sp,
            modifier = Modifier
                .padding(10.dp)
                .weight(1f),
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
        Icon(
            modifier = Modifier
                .padding(10.dp)
                .size(40.dp)
                .weight(1f),
            painter = painterResource(lang.icon),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}
private val ReadingItem: @Composable (readingMode: ReadingMode, onClick: () -> Unit) -> Unit = {
    readingMode, onClick ->
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }, verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = readingMode.name.replace('_', ' '),
            fontSize = 13.sp,
            modifier = Modifier
                .padding(10.dp)
                .weight(1f),
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
        Icon(
            modifier = Modifier
                .padding(10.dp)
                .size(40.dp)
                .weight(1f),
            painter = painterResource(readingMode.icon),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}

@Composable
fun ReadingModeSelector() {
    var readingMode by remember { mutableStateOf(ReadingMode.valueOf(SETTINGS.getReadingMode().toUpperCase(Locale.current))) }
    var showModes by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Manga language ")
        Spacer(modifier = Modifier.width(10.dp))
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ), modifier = Modifier.fillMaxWidth()
        ) {
            LazyColumn(
            ) {
                if (showModes)
                    items(ReadingMode.entries) {
                        ReadingItem(readingMode = it) {
                            setReadingMode(it)
                            readingMode = ReadingMode.valueOf(SETTINGS.getReadingMode().toUpperCase(Locale.current))
                            showModes = false
                        }
                    }
                else
                    item {
                        ReadingItem(readingMode = readingMode) { showModes = true }
                    }
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

private enum class ReadingMode(val icon: Int) {
    PAGE(R.drawable.page),
    CASCADE(R.drawable.cascade)
}

private enum class Languages(
    val value: String, val icon: Int
) {
    SPANISH("es", R.drawable.spain), AMERICAN_ENGLISH("en", R.drawable.uk), UK_ENGLISH(
        "uk",
        R.drawable.us
    ),
    AMERCIAN_SPANISH("es-la", R.drawable.spain), FRENCH(
        "fr",
        R.drawable.french
    ),
    PORTUGAL("pt-br", R.drawable.portugal), RUSSIAN("ru", R.drawable.russia), DEUTSCH(
        "de",
        R.drawable.germany
    ),
    ITALIAN("it", R.drawable.italy);

    companion object{
        fun findByValue(other: String): Languages?{
            return entries.filter { it.value == other }.firstOrNull()
        }
    }
}