package dao

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Manga(
    @SerialName("id") var id: String? = null,
    var name: String? = null,
    var description: String? = null,
    var genres: List<String> = listOf()
)