package io.github.katarem.mangacats.dto.chapter

import com.google.gson.annotations.SerializedName


data class MDChapterResponse (

    @SerializedName("result"   ) var result   : String?               = null,
    @SerializedName("response" ) var response : String?               = null,
    @SerializedName("data"     ) var data     : ArrayList<ChapterDTO> = arrayListOf(),
    @SerializedName("limit"    ) var limit    : Int?                  = null,
    @SerializedName("offset"   ) var offset   : Int?                  = null,
    @SerializedName("total"    ) var total    : Int?                  = null

)