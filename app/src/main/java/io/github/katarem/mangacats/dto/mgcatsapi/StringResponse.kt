package io.github.katarem.mangacats.dto.mgcatsapi

import com.google.gson.annotations.SerializedName

data class StringResponse(
    @SerializedName("message") val message: String
)
