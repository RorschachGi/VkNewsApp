package com.example.vknewsapp.presentation.news

import android.app.Application
import androidx.lifecycle.*
import com.example.vknewsapp.data.model.mapper.NewsFeedMapper
import com.example.vknewsapp.data.network.ApiFactory
import com.example.vknewsapp.domain.FeedPost
import com.example.vknewsapp.domain.StatisticItem
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application): AndroidViewModel(application) {

    private val initialState = NewsFeedScreenState.Initial

    private val _screenState = MutableLiveData<NewsFeedScreenState>(initialState)
    val screenState: LiveData<NewsFeedScreenState>
        get() = _screenState

    private val mapper = NewsFeedMapper()

    init{
        loadRecommendations()
    }

    private fun loadRecommendations(){
        viewModelScope.launch {
            val storage = VKPreferencesKeyValueStorage(getApplication())
            val token = VKAccessToken.restore(storage) ?: return@launch
            val response = ApiFactory.apiService.loadRecommendations(token.accessToken)
            val feedPosts = mapper.mapResponseToPosts(response)
            _screenState.value = NewsFeedScreenState.Posts(posts = feedPosts)
        }
    }

    fun updateCount(feedPost: FeedPost, item: StatisticItem){
        val currentState = screenState.value
        if(currentState !is NewsFeedScreenState.Posts) return

        val oldPosts = currentState.posts.toMutableList()
        val oldStatistics = feedPost.statistics
        val newStatistics = mutableListOf<StatisticItem>()
        oldStatistics.forEach {
            if(it.type == item.type){
                val newStatisticItem = it.copy(count = it.count + 1)
                newStatistics.add(newStatisticItem)
            }else{
                newStatistics.add(it)
            }
        }
        val newFeedPost = feedPost.copy(statistics = newStatistics)
        val newPosts = oldPosts.apply {
            replaceAll {
                if(it.id == newFeedPost.id){
                    newFeedPost
                }else{
                    it
                }
            }
        }
        _screenState.value = NewsFeedScreenState.Posts(posts = newPosts)
    }

    fun remove(feedPost: FeedPost){
        val currentState = _screenState.value
        if(currentState !is NewsFeedScreenState.Posts) return

        val oldPosts = currentState.posts.toMutableList()
        oldPosts.remove(feedPost)
        _screenState.value = NewsFeedScreenState.Posts(posts = oldPosts)
    }

}