package dto

import com.google.gson.annotations.SerializedName


data class Title (

  @SerializedName("en" ) var en : String? = null,
  @SerializedName("ja" ) var jp : String? = null

)