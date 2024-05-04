package io.github.katarem.mangacats.dto

import com.google.gson.annotations.SerializedName
import io.github.katarem.mangacats.dao.LocalManga
import io.github.katarem.mangacats.dto.chapter.ChapterDTO

data class MangaDTO(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("author") val author: String,
    @SerializedName("description") val description: String,
    @SerializedName("cover") var cover_id: String? = null,
    var cover : String? = null,
    var chapters: MutableList<ChapterDTO> = mutableListOf()
){

    fun toLocalManga(suscribed: Boolean, currentChapter: Int = 0): LocalManga {
        return LocalManga(
            uuid = id,
            title = title,
            cover_art = cover ?: "",
            currentChapter = currentChapter,
            suscribed = suscribed
        )
    }

}
