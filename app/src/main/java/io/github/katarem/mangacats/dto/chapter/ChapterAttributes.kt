package io.github.katarem.mangacats.dto.chapter

import com.google.gson.annotations.SerializedName

data class ChapterAttributes(
    @SerializedName("volume"             ) var volume             : String? = null,
    @SerializedName("chapter"            ) var chapter            : String? = null,
    @SerializedName("title"              ) var title              : String? = null,
    @SerializedName("translatedLanguage" ) var translatedLanguage : String? = null,
    @SerializedName("externalUrl"        ) var externalUrl        : String? = null,
    @SerializedName("publishAt"          ) var publishAt          : String? = null,
    @SerializedName("readableAt"         ) var readableAt         : String? = null,
    @SerializedName("createdAt"          ) var createdAt          : String? = null,
    @SerializedName("updatedAt"          ) var updatedAt          : String? = null,
    @SerializedName("pages"              ) var pages              : Int?    = null,
    @SerializedName("version"            ) var version            : Int?    = null
)