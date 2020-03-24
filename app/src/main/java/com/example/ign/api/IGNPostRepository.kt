package com.example.ign.api

class IGNPostRepository(private val api : IGNApi){

    private fun unpackArticlesPosts(response: IGNApi.ListingResponseArticles): List<IGNArticles>? {
        val list = mutableListOf<IGNArticles>()
        for(i in 0 until (response.data.size)){
            list.add(response.data[i])
            //Log.d("test", "Index " + i + " "  + list[i].toString())
        }
        return list
    }

    private fun unpackVideosPosts(response: IGNApi.ListingResponseVideos): List<IGNVideos>? {
        val list = mutableListOf<IGNVideos>()
        for(i in 0 until (response.data.size)){
            list.add(response.data[i])
            //Log.d("test", "Index " + i + " "  + list[i].toString())
        }
        return list
    }

    private fun unpackCommentsPosts(response: IGNApi.ListingResponseComments): List<IGNComments>? {
        val list = mutableListOf<IGNComments>()
        for(i in 0 until (response.count)){
            list.add(response.content[i])
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

    suspend fun getComments(ids: String) : List<IGNComments>? {
        return unpackCommentsPosts(api.getCommentsResponse(ids))
    }
}