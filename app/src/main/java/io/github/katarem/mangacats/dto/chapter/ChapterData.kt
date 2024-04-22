package io.github.katarem.mangacats.dto.chapter

import com.google.gson.annotations.SerializedName

data class ChapterData(
    @SerializedName("id"            ) var id            : String?                  = null,
    @SerializedName("type"          ) var type          : String?                  = null,
    @SerializedName("attributes"    ) var attributes    : ChapterAttributes?              = ChapterAttributes(),
    @SerializedName("relationships" ) var relationships : ArrayList<ChapterRelationships> = arrayListOf()
)