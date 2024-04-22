package io.github.katarem.mangacats.api

import io.github.katarem.mangacats.dto.auth.User
import io.github.katarem.mangacats.dto.mgcatsapi.StringResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {

    @GET("user")
    suspend fun getUser(): User

    @POST("user/register")
    @Headers("Accept: application/json")
    suspend fun registerUser(@Body user: User): Response<User>

    @GET("user/login")
    @Headers("Accept: application/json")
    suspend fun loginUser(@Header("username") username: String, @Header("password") password: String): Response<User>

    @PATCH("user/{id}")
    @Headers("Accept: application/json")
    suspend fun changeProfilePhoto(@Path("id") id: String, @Header("img_url") img: String): Response<StringResponse>

}