package com.example.vknewsapp.presentation.news

import android.app.Application
import androidx.lifecycle.*
import com.example.vknewsapp.data.repository.NewsFeedRepository
import com.example.vknewsapp.domain.FeedPost
import com.example.vknewsapp.domain.StatisticItem
import com.example.vknewsapp.extensions.mergeWith
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application): AndroidViewModel(application) {

    private val repository = NewsFeedRepository(application)

    private val recommendationsFlow = repository.recommendations

    private val loadNextDataFlow = MutableSharedFlow<NewsFeedScreenState.Posts>()


    val screenState = recommendationsFlow
        .filter { it.isNotEmpty() }
        .map { NewsFeedScreenState.Posts(posts = it) as NewsFeedScreenState }
        .onStart { emit(NewsFeedScreenState.Loading) }
        .mergeWith(loadNextDataFlow)


    fun loadNextRecommendation(){
        viewModelScope.launch {
            loadNextDataFlow.emit(
                NewsFeedScreenState.Posts(
                    posts = recommendationsFlow.value,
                    nextDataIsLoading = true
                )
            )
            repository.loadNextData()
        }
    }

    fun changeLikeStatus(feedPost: FeedPost){
        viewModelScope.launch {
            repository.changeLikeStatus(feedPost)
        }
    }

    fun remove(feedPost: FeedPost){
        viewModelScope.launch {
            repository.deletePost(feedPost)
        }
    }

}