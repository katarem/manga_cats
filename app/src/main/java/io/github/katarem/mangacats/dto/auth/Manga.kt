package io.github.katarem.mangacats.dto.auth

import com.google.gson.annotations.SerializedName
import io.github.katarem.mangacats.dto.MangaDAO

data class Manga(
    @SerializedName("id") val id: String,
    @SerializedName("name") val title: String,
    @SerializedName("cover_art") val cover_art: String,
    @SerializedName("currentChapter") var currentChapter: Int,
    @SerializedName("suscribed") var suscribed: Boolean = false
){

    fun fromMangaDAO(mangaDAO: MangaDAO): Manga{
        return Manga(
            id = mangaDAO.id,
            cover_art = mangaDAO.cover!!,
            currentChapter = 1,
            title = mangaDAO.title
        )
    }

}