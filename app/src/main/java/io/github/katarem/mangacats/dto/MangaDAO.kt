package io.github.katarem.mangacats.dto

import com.google.gson.annotations.SerializedName
import io.github.katarem.mangacats.dto.chapter.ChapterDTO

data class MangaDAO(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("author") val author: String,
    @SerializedName("description") val description: String,
    @SerializedName("cover") var cover_id: String? = null,
    var cover : String? = null,
    var chapters: MutableList<ChapterDTO> = mutableListOf()
)
