package io.github.katarem.mangacats.dto.simple

import com.google.gson.annotations.SerializedName
import io.github.katarem.mangacats.dto.MangaDAO


data class SimpleMDResponse (

    @SerializedName("result"   ) var result   : String?         = null,
    @SerializedName("response" ) var response : String?         = null,
    @SerializedName("data"     ) var data     : SimpleMDData?     = null,
    @SerializedName("limit"    ) var limit    : Int?            = null,
    @SerializedName("offset"   ) var offset   : Int?            = null,
    @SerializedName("total"    ) var total    : Int?            = null

){

  fun getManga(): MangaDAO{
    data.let {
      return MangaDAO(
        it!!.id!!,
        it.attributes!!.title!!.en!!,
        "autor",
        it.attributes!!.description!!.toString(),
        "foto"
      )
    }
  }



}