package nav

import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import screens.HomeScreen

@Composable
fun Router(){
    val navigator = rememberNavigator()
    NavHost(
        navigator = navigator,
        navTransition = NavTransition(),
        initialRoute = Routes.home
    ){
        scene(
            route = Routes.home,
            navTransition = NavTransition()
        ){
            HomeScreen(navigator = navigator)
        }
        
        
        
        
    }
    
    
    
}