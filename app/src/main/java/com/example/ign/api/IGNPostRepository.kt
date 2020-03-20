package com.example.ign.api

import com.google.gson.Gson

class IGNPostRepository(private val api : IGNApi){

    fun unpackArticlesPosts(response: IGNApi.ListingResponseArticles): List<IGNArticles>? {
        val list = mutableListOf<IGNArticles>()
        for(i in 0 until (response.data.size)){
            list.add(response.data[i])
            //Log.d("test", "Index " + i + " "  + list[i].toString())
        }
        return list
    }

    fun unpackVideosPosts(response: IGNApi.ListingResponseVideos): List<IGNVideos>? {
        val list = mutableListOf<IGNVideos>()
        for(i in 0 until (response.data.size)){
            list.add(response.data[i])
            //Log.d("test", "Index " + i + " "  + list[i].toString())
        }
        return list
    }

    suspend fun getArticles(start: Int, count: Int): List<IGNArticles>? {
        return unpackArticlesPosts(api.getArticlesResponse(start, count))
    }

    suspend fun getVideos(start: Int, count: Int): List<IGNVideos>? {
        return unpackVideosPosts(api.getVideosResponse(start, count))
    }
}