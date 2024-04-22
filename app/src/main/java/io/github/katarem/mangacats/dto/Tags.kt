package dto

import com.google.gson.annotations.SerializedName


data class Tags (

  @SerializedName("id"            ) var id            : String?           = null,
  @SerializedName("type"          ) var type          : String?           = null,
  @SerializedName("attributes"    ) var attributes    : Attributes?       = Attributes(),
  @SerializedName("relationships" ) var relationships : ArrayList<String> = arrayListOf()

)