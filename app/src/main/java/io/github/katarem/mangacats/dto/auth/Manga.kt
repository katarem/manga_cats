package io.github.katarem.mangacats.dto.auth

import com.google.gson.annotations.SerializedName
import io.github.katarem.mangacats.dto.MangaDTO

data class Manga(
    @SerializedName("id") val id: String,
    @SerializedName("name") val title: String,
    @SerializedName("cover_art") val cover_art: String,
    @SerializedName("currentChapter") var currentChapter: Int,
    @SerializedName("suscribed") var suscribed: Boolean = false
){

    fun fromMangaDAO(mangaDTO: MangaDTO): Manga{
        return Manga(
            id = mangaDTO.id,
            cover_art = mangaDTO.cover!!,
            currentChapter = 1,
            title = mangaDTO.title
        )
    }

}