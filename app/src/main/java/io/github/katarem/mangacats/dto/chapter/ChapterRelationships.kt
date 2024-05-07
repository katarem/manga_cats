package io.github.katarem.mangacats.dto.chapter

import com.google.gson.annotations.SerializedName
import dto.Attributes
import dto.SimpleMDAttributes

data class ChapterRelationships (

    @SerializedName("id"   ) var id   : String? = null,
    @SerializedName("type" ) var type : String? = null,
    @SerializedName("attributes") var attributes : Attributes? = Attributes()


)