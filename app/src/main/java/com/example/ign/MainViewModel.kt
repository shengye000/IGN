package com.example.ign

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ign.api.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private var articlesPost = MutableLiveData<List<IGNArticles>>()
    private var videosPost = MutableLiveData<List<IGNVideos>>()
    private var articlesComment = MutableLiveData<List<IGNComments>>()
    private var videosComment = MutableLiveData<List<IGNComments>>()
    private var startIndex = 30 //30
    private var count = 5  //5

    private val ignApi = IGNApi.create()
    private val ignRepository = IGNPostRepository(ignApi)

    fun netFetchArticlesPost() = viewModelScope.launch(
        context = viewModelScope.coroutineContext + Dispatchers.IO
    ){
        articlesPost.postValue(ignRepository.getArticles(startIndex, count))
    }

    fun observeArticlesPost(): LiveData<List<IGNArticles>> {
        return articlesPost
    }

    fun netFetchVideosPost() = viewModelScope.launch(
        context = viewModelScope.coroutineContext + Dispatchers.IO
    ){
        videosPost.postValue(ignRepository.getVideos(startIndex, count))
    }

    fun observeVideosPost(): LiveData<List<IGNVideos>> {
        return videosPost
    }

    fun netFetchArticleCommentsPost(ids: String) = viewModelScope.launch(
        context = viewModelScope.coroutineContext + Dispatchers.IO
    ){
        articlesComment.postValue(ignRepository.getComments(ids))
    }

    fun observeArticleCommentsPost(): LiveData<List<IGNComments>> {
        return articlesComment
    }

}