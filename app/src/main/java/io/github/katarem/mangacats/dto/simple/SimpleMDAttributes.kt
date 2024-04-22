package dto

import com.google.gson.annotations.SerializedName


data class SimpleMDAttributes (

  @SerializedName("title"                          ) var title                          : Title?               = Title(),
  @SerializedName("altTitles"                      ) var altTitles                      : ArrayList<AltTitles> = arrayListOf(),
  @SerializedName("description"                    ) var description                    : String?              = null,
  @SerializedName("isLocked"                       ) var isLocked                       : Boolean?             = null,
  @SerializedName("links"                          ) var links                          : Links?               = Links(),
  @SerializedName("originalLanguage"               ) var originalLanguage               : String?              = null,
  @SerializedName("lastVolume"                     ) var lastVolume                     : String?              = null,
  @SerializedName("lastChapter"                    ) var lastChapter                    : String?              = null,
  @SerializedName("publicationDemographic"         ) var publicationDemographic         : String?              = null,
  @SerializedName("status"                         ) var status                         : String?              = null,
  @SerializedName("year"                           ) var year                           : Int?                 = null,
  @SerializedName("contentRating"                  ) var contentRating                  : String?              = null,
  @SerializedName("tags"                           ) var tags                           : ArrayList<Tags>      = arrayListOf(),
  @SerializedName("state"                          ) var state                          : String?              = null,
  @SerializedName("chapterNumbersResetOnNewVolume" ) var chapterNumbersResetOnNewVolume : Boolean?             = null,
  @SerializedName("createdAt"                      ) var createdAt                      : String?              = null,
  @SerializedName("updatedAt"                      ) var updatedAt                      : String?              = null,
  @SerializedName("version"                        ) var version                        : Int?                 = null,
  @SerializedName("availableTranslatedLanguages"   ) var availableTranslatedLanguages   : ArrayList<String>    = arrayListOf(),
  @SerializedName("latestUploadedChapter"          ) var latestUploadedChapter          : String?              = null,
  @SerializedName("fileName"                       ) var fileName                       : String?              = null,
)