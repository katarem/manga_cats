package io.github.katarem.mangacats.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.github.katarem.mangacats.R
import io.github.katarem.mangacats.components.CustomTextField
import io.github.katarem.mangacats.nav.Routes
import io.github.katarem.mangacats.utils.Status
import io.github.katarem.mangacats.viewmodel.CredentialsViewModel

@Composable
fun LoginScreen(navController: NavController?, credentialsViewModel: CredentialsViewModel){
    val isLogin = credentialsViewModel.isLogin.collectAsState()
    val status = credentialsViewModel.status.collectAsState()
    val loginMessage = credentialsViewModel.loginMessage.collectAsState()
    val messageColor = if(status.value == Status.SUCCESS) Color.Green else Color.Red
    if(status.value == Status.SUCCESS) {
        navController?.popBackStack()
        credentialsViewModel.clearMessage()
    }
    if(isLogin.value)LoginForm(credentialsViewModel = credentialsViewModel)
    else RegisterForm(credentialsViewModel = credentialsViewModel)
    Text(text = loginMessage.value, color = messageColor)

}

@Composable
fun RegisterForm(credentialsViewModel: CredentialsViewModel){
    val username = credentialsViewModel.username.collectAsState()
    val password = credentialsViewModel.password.collectAsState()
    val confirmPassword = credentialsViewModel.confirmPassword.collectAsState()
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp),horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth()
                .padding(10.dp),horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.Center
        ) {
            CustomTextField(value = username.value, onValueChange = { credentialsViewModel.setUsername(it) }, placeholder = "Email", painter = rememberVectorPainter(image = Icons.Filled.Email), modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp))
            CustomTextField(value = password.value, onValueChange = { credentialsViewModel.setPassword(it) }, placeholder = "Password", painter = painterResource(id = R.drawable.key), modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp), visualTransformation = PasswordVisualTransformation())
            CustomTextField(value = confirmPassword.value, onValueChange = { credentialsViewModel.setConfirmPassword(it) }, placeholder = "Confirm Password", painter = painterResource(id = R.drawable.key), modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),visualTransformation = PasswordVisualTransformation())
            Text(text = "Do you already have an account? Log in", textAlign = TextAlign.Center,textDecoration = TextDecoration.Underline, modifier = Modifier
                .clickable { credentialsViewModel.changeMode(true) }
                .fillMaxWidth()
                .padding(10.dp))
            Button(onClick = { credentialsViewModel.register() }, modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)) {Text(text = "Register") }
        }
    }



}

@Composable
fun LoginForm(credentialsViewModel: CredentialsViewModel){
    val username = credentialsViewModel.username.collectAsState()
    val password = credentialsViewModel.password.collectAsState()
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp),horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(0.5f).fillMaxWidth().padding(10.dp),horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.Center
        ) {
            CustomTextField(value = username.value, onValueChange = { credentialsViewModel.setUsername(it) },placeholder = "Email", painter = rememberVectorPainter(image = Icons.Filled.Email), modifier = Modifier.fillMaxWidth().padding(10.dp))
            CustomTextField(value = password.value, onValueChange = { credentialsViewModel.setPassword(it) },placeholder = "Password", painter = painterResource(id = R.drawable.key), modifier = Modifier.fillMaxWidth().padding(10.dp),visualTransformation = PasswordVisualTransformation())
            Text(text = "Don't you have an account? Sign in", textAlign = TextAlign.Center,textDecoration = TextDecoration.Underline, modifier = Modifier.clickable { credentialsViewModel.changeMode(false) }.fillMaxWidth().padding(10.dp))
            Button(onClick = { credentialsViewModel.login() }, modifier = Modifier.fillMaxWidth().padding(10.dp)) {Text(text = "Login")}
        }
    }




}