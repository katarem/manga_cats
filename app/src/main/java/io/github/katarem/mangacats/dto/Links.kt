package dto

import com.google.gson.annotations.SerializedName


data class Links (

  @SerializedName("al"    ) var al    : String? = null,
  @SerializedName("ap"    ) var ap    : String? = null,
  @SerializedName("bw"    ) var bw    : String? = null,
  @SerializedName("kt"    ) var kt    : String? = null,
  @SerializedName("mu"    ) var mu    : String? = null,
  @SerializedName("amz"   ) var amz   : String? = null,
  @SerializedName("cdj"   ) var cdj   : String? = null,
  @SerializedName("ebj"   ) var ebj   : String? = null,
  @SerializedName("mal"   ) var mal   : String? = null,
  @SerializedName("raw"   ) var raw   : String? = null,
  @SerializedName("engtl" ) var engtl : String? = null

)