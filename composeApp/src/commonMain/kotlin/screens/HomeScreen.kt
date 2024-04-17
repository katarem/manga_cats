package screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dao.Manga
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.viewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import viewmodel.HomeViewModel

@Composable
fun HomeScreen(navigator: Navigator?){
    
    val model: HomeViewModel = viewModel(keys = listOf()){
        HomeViewModel()
    }
    val pagina = remember { mutableStateOf(1) }
    val manga = remember { mutableStateOf(model.manga) }
    
    LaunchedEffect(Unit){
        model.getManga("one piece")
    }
    
    Surface {
           Column(
               modifier = Modifier.fillMaxSize(),
               horizontalAlignment = Alignment.CenterHorizontally,
               verticalArrangement = Arrangement.Center
           ) {
//               KamelImage(
//                   painter,
//                   contentDescription = null,
//                   modifier = Modifier.fillMaxWidth()
//               )
               Text("Estás leyendo ${manga.value.id?:"MANGA 1"}")
               Text("Estás leyendo ${manga.value.name?:"manga generico"}")
               Text("Estás leyendo ${manga.value.description?:"descripcion generica"}")
               
           }
           Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween) {
               Box( modifier = Modifier.fillMaxHeight().fillMaxWidth(0.1f).clickable { pagina.value -= 1 },) {  }
               Box( modifier = Modifier.fillMaxHeight().fillMaxWidth(0.1f).clickable { pagina.value += 1 }) { }
           }
       }
    
    
    
}


@Composable
fun MangaCard(manga: Manga){
    Box(
        modifier = Modifier.fillMaxSize(0.2f).background(Color(252, 186, 3)).clip(RoundedCornerShape(5.dp))
    ) {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize().padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(modifier = Modifier.weight(1f).padding(5.dp)) {
                    Text(manga.id?:"")
                }
                Text(manga.name?:"", modifier = Modifier.weight(1f))
            }
        }
    }
}

@Preview
@Composable
fun MangaCardPreview(){
    MangaCard(Manga("1234","one piece"))
}