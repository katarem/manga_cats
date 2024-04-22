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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import io.github.katarem.mangacats.viewmodel.CredentialsViewModel

@Composable
fun SettingsScreen(navController: NavController?, credentialsViewModel: CredentialsViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        UserControls(credentialsViewModel = credentialsViewModel, navController = navController)
        MangaLanguageSelector()
        ReadingModeSelector()
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
                Text(text = it.name, modifier = Modifier.clickable {
                    setLanguage(it)
                    mangaLang.value = SETTINGS.getMangaLang()
                })
            }
        }
    }
}

@Composable
fun UserControls(credentialsViewModel: CredentialsViewModel, navController: NavController?) {
    val user = credentialsViewModel.user.collectAsState()
    AsyncImage(
        modifier = Modifier
            .fillMaxWidth(0.4f)
            .fillMaxHeight(0.2f)
            .padding(10.dp)
            .clip(
                CircleShape
            ),
        model = user.value?.profileImg ?: profileDefault,
        contentDescription = "",
        contentScale = ContentScale.Crop
    )
    Text(
        text = user.value?.username ?: "Not Registered User",
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold
    )
    if (credentialsViewModel.status.collectAsState().value == Status.SUCCESS) {
        DisplayButton(
            title = "Change profile's image"
        ) {
            val profileImg = remember { mutableStateOf("") }
            val photoStatus = credentialsViewModel.loginMessage.collectAsState()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Add the new profile picture's URL here")
                CustomTextField(
                    value = profileImg.value,
                    onValueChange = { profileImg.value = it },
                    painter = null,
                    placeholder = "URL"
                )
                Button(onClick = { credentialsViewModel.changeProfilePhoto(profileImg.value) }) {
                    Text(text = "Update photo")
                }
                Text(text = photoStatus.value)
            }
        }
        Button(onClick = {
            credentialsViewModel.logout()
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
            Text(text = "Log Out")
        }
    } else Button(onClick = { navController?.navigate(Routes.LOGIN) }) {
        Text(text = "Log In")
    }
    Text(text = "Version $CURRENT_VERSION")
}


@Composable
fun ReadingModeSelector() {
    val readingMode = remember { mutableStateOf(SETTINGS.getReadingMode()) }
    DisplayButton(
        title = "Reading Mode: ${readingMode.value}"
    ) {
        LazyColumn {
            items(ReadingMode.entries) {
                Text(text = it.name, modifier = Modifier.clickable {
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
    val selectedLang = when (lang) {
        Languages.SPANISH -> "es"
        Languages.AMERICAN_ENGLISH -> "en"
        Languages.AMERCIAN_SPANISH -> "es-la"
        Languages.FRENCH -> "fr"
        Languages.PORTUGUESE -> "pt-br"
        Languages.UK_ENGLISH -> "uk"
        Languages.RUSSIAN -> "ru"
        Languages.DEUTSCH -> "de"
        Languages.ITALIAN -> "it"
    }
    SETTINGS.setMangaLang(selectedLang)
}

private enum class ReadingMode {
    PAGE,
    CASCADE
}

private enum class Languages {
    SPANISH,
    AMERCIAN_SPANISH,
    AMERICAN_ENGLISH,
    UK_ENGLISH,
    FRENCH,
    PORTUGUESE,
    RUSSIAN,
    DEUTSCH,
    ITALIAN
}