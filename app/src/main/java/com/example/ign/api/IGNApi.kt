package com.example.ign.api

import com.google.gson.GsonBuilder
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface IGNApi {

    @GET("/articles")
    suspend fun getArticlesResponse(@Query("startIndex") start: Int,
                                 @Query("count") count: Int): ListingResponseArticles

    @GET("/videos")
    suspend fun getVideosResponse(@Query("startIndex") start: Int,
                                    @Query("count") count: Int): ListingResponseVideos

    @GET("/comments")
    suspend fun getCommentsResponse(@Query("ids") ids: String): ListingResponseComments

    class ListingResponseArticles(
        val count: Int,
        val startIndex: Int,
        val data: List<IGNArticles>
    )

    class ListingResponseVideos(
        val count: Int,
        val startIndex: Int,
        val data: List<IGNVideos>
    )

    class ListingResponseComments(
        val count: Int,
        val content: List<IGNComments>
    )

    companion object {
        private fun buildGsonConverterFactory(): GsonConverterFactory {
            val gsonBuilder = GsonBuilder()
            return GsonConverterFactory.create(gsonBuilder.create())
        }
        //private const val BASE_URL = "https://ign-apis.herokuapp.com"
        var httpurl = HttpUrl.Builder()
            .scheme("https")
            .host("ign-apis.herokuapp.com")
            .build()
        fun create(): IGNApi = create(httpurl)
        private fun create(httpUrl: HttpUrl): IGNApi {

            val client = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    this.level = HttpLoggingInterceptor.Level.BASIC
                })
                .build()
            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(client)
                .addConverterFactory(buildGsonConverterFactory())
                .build()
                .create(IGNApi::class.java)
        }
    }
}