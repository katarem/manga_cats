package io.github.katarem.mangacats.api

import io.github.katarem.mangacats.dto.auth.Manga
import io.github.katarem.mangacats.dto.mgcatsapi.StringResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface MangaService {

    @GET("user/{id}/mangas/suscribed")
    suspend fun getUserSuscribedMangas(@Path("id") id: String): Response<List<Manga>>

    @PATCH("user/{id}/mangas/suscribed")
    suspend fun updateUserSuscribedMangas(@Path("id") id: String, @Body mangas: List<Manga>): Response<StringResponse>

    @GET("user/{id}/mangas/recent")
    suspend fun getUserRecentMangas(@Path("id") id: String): Response<List<Manga>>

    @PATCH("user/{id}/mangas/recent")
    suspend fun updateUserRecentMangas(@Path("id") id: String, @Body mangas: List<Manga>): Response<StringResponse>

}