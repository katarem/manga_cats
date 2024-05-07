package io.github.katarem.mangacats

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import io.github.katarem.mangacats.dao.LocalDatabase
import io.github.katarem.mangacats.nav.Router
import io.github.katarem.mangacats.ui.theme.MangaCatsTheme
import io.github.katarem.mangacats.utils.SETTINGS
import io.github.katarem.mangacats.utils.Settings

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("suspension","me creoxd")
        super.onCreate(savedInstanceState)
        setContent {
            MangaCatsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                    SETTINGS = Settings(context)
                    LocalDatabase.createInstance(context)
                    Router()
                }
            }
        }
    }
}







