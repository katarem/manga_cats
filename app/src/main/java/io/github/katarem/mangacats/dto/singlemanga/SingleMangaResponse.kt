package io.github.katarem.mangacats.dto.singlemanga

import com.google.gson.annotations.SerializedName
import dto.Data
import io.github.katarem.mangacats.dto.MangaDTO

data class SingleMangaResponse(
    @SerializedName("result"   ) var result   : String?         = null,
    @SerializedName("response" ) var response : String?         = null,
    @SerializedName("data"     ) var data     : Data            = Data(),
    @SerializedName("limit"    ) var limit    : Int?            = null,
    @SerializedName("offset"   ) var offset   : Int?            = null,
    @SerializedName("total"    ) var total    : Int?            = null
){
    fun getManga(): MangaDTO {
        data.let { dataInfo ->
            val id = dataInfo.id!!
            return MangaDTO(
                id = id,
                title = dataInfo.attributes!!.title!!.en?:dataInfo.attributes!!.title!!.jp?:"[NOT RECOGNISED TITLE]",
                author = "autor",
                description = dataInfo.attributes!!.description,
            )
        }
    }
}


