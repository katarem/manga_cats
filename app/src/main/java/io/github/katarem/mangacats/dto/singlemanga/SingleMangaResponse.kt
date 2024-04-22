package io.github.katarem.mangacats.dto.singlemanga

import com.google.gson.annotations.SerializedName
import dto.Data
import io.github.katarem.mangacats.dto.MangaDAO

data class SingleMangaResponse(
    @SerializedName("result"   ) var result   : String?         = null,
    @SerializedName("response" ) var response : String?         = null,
    @SerializedName("data"     ) var data     : Data            = Data(),
    @SerializedName("limit"    ) var limit    : Int?            = null,
    @SerializedName("offset"   ) var offset   : Int?            = null,
    @SerializedName("total"    ) var total    : Int?            = null
){
    fun getManga(): MangaDAO {
        data.let { dataInfo ->
            //val coverId = dataInfo.relationships.singleOrNull { it.type == "cover_art" }?.id
            //val fileName = dataInfo.relationships.singleOrNull{ it.type == "cover_art" }?.attributes?.fileName!!
            val id = dataInfo.id!!
            //val cover = "https://uploads.mangadex.org/covers/$id/$fileName"
            return MangaDAO(
                id = id,
                title = dataInfo.attributes!!.title!!.en?:dataInfo.attributes!!.title!!.jp?:"[NOT RECOGNISED TITLE]",
                author = "autor",
                description = dataInfo.attributes!!.description!!.toString(),
                //cover_id = coverId,
              //  cover = cover
            )
        }
    }
}


