package io.github.katarem.mangacats.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.katarem.mangacats.api.Retrofit
import io.github.katarem.mangacats.dto.auth.User
import io.github.katarem.mangacats.utils.SETTINGS
import io.github.katarem.mangacats.utils.Status
import io.github.katarem.mangacats.utils.hashPassword
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CredentialsViewModel : ViewModel(){

    private val service = Retrofit.userService

    private var _status = MutableStateFlow(Status.IDLE)
    val status = _status.asStateFlow()
    private var _isLogin = MutableStateFlow(false)
    val isLogin = _isLogin.asStateFlow()
    private var _user = MutableStateFlow(null as User?)
    val user = _user.asStateFlow()
    private var _username = MutableStateFlow("")
    val username = _username.asStateFlow()
    private var _password = MutableStateFlow("")
    val password = _password.asStateFlow()
    private var _confirmPassword = MutableStateFlow("")
    val confirmPassword = _confirmPassword.asStateFlow()
    private var _loginMessage = MutableStateFlow("")
    val loginMessage = _loginMessage.asStateFlow()


    fun clearMessage(){
        _loginMessage.update { "" }
    }

    fun changeProfilePhoto(newValue: String){
        try {
            _status.update { Status.LOADING }
            viewModelScope.launch {
                val response = service.changeProfilePhoto(_user.value!!.username,newValue)
                Log.d("photo","${response.body()}")
                _user.update { _user.value!!.copy( profileImg = newValue ) }
                SETTINGS.saveUser(_user.value!!)
                _status.update { Status.SUCCESS }
            }
        } catch (ex: Exception){
            Log.d("photo","ERROR $ex")
            _status.update { Status.ERROR }
        }
    }

    fun setUser(user: User?): Boolean{
        _user.update { user }
        return _user.value != null
    }

    fun setStatus(newStatus: Status){
        _status.update { newStatus }
    }


    fun logout(){
        _status.update { Status.IDLE }
        _user.update { null }
        _username.update { "" }
        _password.update { "" }
        _confirmPassword.update { "" }
        _loginMessage.update { "" }
        SETTINGS.clearAccount()
    }

    fun setUsername(username: String){
        _username.value = username
    }

    fun setPassword(password: String){
        _password.value = password
    }

    fun setConfirmPassword(confirmPassword: String){
        _confirmPassword.value = confirmPassword
    }

    private fun checkPasswords(): Boolean{
        return _password.value == _confirmPassword.value
    }

    fun login(){
        if(_status.value != Status.LOADING) {
            _status.update { Status.LOADING }
            //_password.update { hashPassword(_password.value) }
            viewModelScope.launch {
                val hashPassword = hashPassword(_password.value)
                val response = service.loginUser(_username.value, hashPassword)
                Log.d("testUsers", "HTTP ${response.code()}")
                Log.d("testUsers", "BODY ${response.body()}")
                when (response.code()) {
                    200 -> {
                        _loginMessage.update { "Login Exitoso!" }
                        _user.update { response.body() }
                        _status.update { Status.SUCCESS }
                        SETTINGS.saveUser(_user.value!!)
                    }

                    400 -> {
                        _loginMessage.update { "Usuario o contraseña inválidos" }
                        _status.update { Status.ERROR }
                    }

                    else -> {
                        _loginMessage.update { "ERROR ${response.message()}" }
                        _status.update { Status.ERROR }
                    }
                }

            }
        }
    }

    fun register(){
        if(_status.value != Status.LOADING && checkPasswords()) {
            //_password.update { hashPassword(_password.value) }
            viewModelScope.launch {
                val hashPassword = hashPassword(_password.value)
                val response = service.registerUser(User(username = _username.value, password = hashPassword ))
                Log.d("testUsers","HTTP ${response.code()}")
                Log.d("testUsers","BODY ${response.body()}")
                when(response.code()){
                    200 -> {
                        _loginMessage.update { "Registro exitoso!" }
                        _user.update { response.body() }
                        _status.update { Status.SUCCESS }
                        SETTINGS.saveUser(_user.value!!)
                    }
                    400 -> {
                        _loginMessage.update { "Usuario o contraseña inválidos" }
                        _status.update { Status.ERROR }
                    }
                    else -> {
                        _loginMessage.update { "ERROR ${response.message()}" }
                        _status.update { Status.ERROR }
                    }
                }
            }
        }
    }

    fun changeMode(mode: Boolean){
        _isLogin.update { mode }
    }


}