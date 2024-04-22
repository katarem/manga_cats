package io.github.katarem.mangacats.api

import dto.MDResponse
import io.github.katarem.mangacats.dto.simple.SimpleMDResponse
import io.github.katarem.mangacats.dto.MangaDAO
import io.github.katarem.mangacats.dto.chapter.MDChapterResponse
import io.github.katarem.mangacats.dto.chapterpages.ChapterPagesResponse
import io.github.katarem.mangacats.dto.singlemanga.SingleMangaResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MangaDexService {

    @GET("manga/{id}")
    suspend fun getManga(@Path("id") id: String): SingleMangaResponse

    @GET("manga")
    suspend fun getMangaByTitle(
        @Query("limit") limit: Int = 20,
        @Query("includedTagsMode") includedTagsMode: String = "AND",
        @Query("excludedTagsMode") excludedTagsMode: String = "OR",
        @Query("contentRating[]") contentRating: List<String> = listOf("safe", "suggestive", "erotica"),
        @Query("order[latestUploadedChapter]") order: String = "desc",
        @Query("availableTranslatedLanguage[]") lang: List<String> = listOf("en"),
        @Query("includes[]") includes: List<String> = listOf("cover_art"),
        @Query("title") title: String = ""
    ): MDResponse


    @GET("manga/{mangaId}/feed")
    suspend fun getChaptersFeed(
        @Path("mangaId") mangaId: String,
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int = 0,
        @Query("translatedLanguage[]") translatedLanguage: String = "es",
        @Query("contentRating[]") contentRating: List<String> = listOf("safe", "suggestive", "erotica"),
        @Query("includeFutureUpdates") includeFutureUpdates: Int = 1,
        @Query("order[chapter]") orderChapter: String = "asc",
        @Query("includes[]") includes: List<String> = listOf("manga"),
        @Query("includeEmptyPages") includeEmptyPages: Int = 0,
        @Query("includeFuturePublishAt") includeFuturePublishAt: Int = 0,
        @Query("includeExternalUrl") includeExternalUrl: Int = 0
    ): MDChapterResponse

    @GET("at-home/server/{chapterId}")
    suspend fun getPagesByChapterId(@Path("chapterId") chapterId: String): ChapterPagesResponse

}