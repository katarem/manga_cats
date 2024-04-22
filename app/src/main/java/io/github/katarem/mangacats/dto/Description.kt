package dto

import com.google.gson.annotations.SerializedName


data class Description (
    @SerializedName("en"    ) var en    : String? = null,
    @SerializedName("ru"    ) var ru    : String? = null,
    @SerializedName("uk"    ) var uk    : String? = null,
    @SerializedName("es-la" ) var es_la : String? = null,
    @SerializedName("pt-br" ) var pt_br : String? = null

)