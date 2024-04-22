package io.github.katarem.mangacats.dto.chapter

import com.google.gson.annotations.SerializedName


data class Volume (

    @SerializedName("volume"            )  var volume: String? = null,
    @SerializedName("count"            )  var count: Int? = null,
    @SerializedName("chapters"            )  var chapters: Map<String, ChapterDTO> = mapOf(),

)