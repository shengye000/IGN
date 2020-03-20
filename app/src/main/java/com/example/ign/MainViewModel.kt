package com.example.ign

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ign.api.IGNApi
import com.example.ign.api.IGNArticles
import com.example.ign.api.IGNPostRepository
import com.example.ign.api.IGNVideos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private var articlesPost = MutableLiveData<List<IGNArticles>>()
    private var videosPost = MutableLiveData<List<IGNVideos>>()
    private var startIndex = 30
    private var count = 5

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


}