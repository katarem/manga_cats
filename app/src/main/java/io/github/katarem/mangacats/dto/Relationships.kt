package dto

import com.google.gson.annotations.SerializedName


data class Relationships (

  @SerializedName("id"   ) var id   : String? = null,
  @SerializedName("type" ) var type : String? = null,
  @SerializedName("attributes") var attributes : SimpleMDAttributes? = SimpleMDAttributes()


)