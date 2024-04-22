package io.github.katarem.mangacats.api

object Retrofit {
    private const val BASE_URL_MANGADEX = "https://api.mangadex.org/"
    private const val BASE_URL_API = "http://192.168.1.38:8080/"
    val mangadexService: MangaDexService by lazy {
        retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL_MANGADEX)
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
            .create(MangaDexService::class.java)
    }
    val userService: UserService by lazy{
        retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL_API)
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
            .create(UserService::class.java)
    }
    val mangaService: MangaService by lazy{
        retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL_API)
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
            .create(MangaService::class.java)
    }
}