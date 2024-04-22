package dto

import com.google.gson.annotations.SerializedName
import io.github.katarem.mangacats.dto.MangaDAO
import io.github.katarem.mangacats.utils.coverDefault


data class MDResponse (

  @SerializedName("result"   ) var result   : String?         = null,
  @SerializedName("response" ) var response : String?         = null,
  @SerializedName("data"     ) var data     : ArrayList<Data> = arrayListOf(),
  @SerializedName("limit"    ) var limit    : Int?            = null,
  @SerializedName("offset"   ) var offset   : Int?            = null,
  @SerializedName("total"    ) var total    : Int?            = null

){

  fun getManga(data: Data): MangaDAO{
    data.let { dataInfo ->
      val coverId = dataInfo.relationships.singleOrNull { it.type == "cover_art" }?.id
      val fileName = dataInfo.relationships.singleOrNull{ it.type == "cover_art" }?.attributes?.fileName!!
      val id = dataInfo.id!!
      val cover = "https://uploads.mangadex.org/covers/$id/$fileName"
      return MangaDAO(
        id = id,
        title = dataInfo.attributes!!.title!!.en?:dataInfo.attributes!!.title!!.jp?:"[NOT RECOGNISED TITLE]",
        author = "autor",
        description = dataInfo.attributes!!.description!!.toString(),
        cover_id = coverId,
        cover = cover
      )
    }
  }


  fun getMangaList(): MutableList<MangaDAO> {
    return data.map { getManga(it) } as MutableList<MangaDAO>
  }


}