package io.github.katarem.mangacats.dto.simple

import com.google.gson.annotations.SerializedName
import dto.Relationships
import dto.SimpleMDAttributes


data class SimpleMDData (

    @SerializedName("id"            ) var id            : String?                  = null,
    @SerializedName("type"          ) var type          : String?                  = null,
    @SerializedName("attributes"    ) var attributes    : SimpleMDAttributes?      = SimpleMDAttributes(),
    @SerializedName("relationships" ) var relationships : ArrayList<Relationships> = arrayListOf()
)