package io.github.katarem.mangacats.dto.chapterpages

import com.google.gson.annotations.SerializedName


data class ChapterPagesResponse (

    @SerializedName("result"  ) var result  : String?  = null,
    @SerializedName("baseUrl" ) var baseUrl : String?  = null,
    @SerializedName("chapter" ) var chapterPages : ChapterPages? = ChapterPages()

)