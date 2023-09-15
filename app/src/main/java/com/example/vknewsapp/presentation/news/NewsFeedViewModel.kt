package com.example.vknewsapp.presentation.news

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.vknewsapp.data.repository.NewsFeedRepositoryImpl
import com.example.vknewsapp.domain.entity.FeedPost
import com.example.vknewsapp.domain.usecases.ChangeLikeStatusUseCase
import com.example.vknewsapp.domain.usecases.DeletePostUseCase
import com.example.vknewsapp.domain.usecases.GetRecommendationsUseCase
import com.example.vknewsapp.domain.usecases.LoadNextDataUseCase
import com.example.vknewsapp.extensions.mergeWith
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application): AndroidViewModel(application) {

    private val exceptionHandler = CoroutineExceptionHandler{_, _ ->
        Log.d("NewsFeedViewModel", "Exception caught by exception handler")
    }

    private val repository = NewsFeedRepositoryImpl(application)

    private val getRecommendationsUseCase = GetRecommendationsUseCase(repository)
    private val loadNextDataUseCase = LoadNextDataUseCase(repository)
    private val changeLikeStatusUseCase = ChangeLikeStatusUseCase(repository)
    private val deletePostUseCase = DeletePostUseCase(repository)

    private val recommendationsFlow = getRecommendationsUseCase()

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
            loadNextDataUseCase()
        }
    }

    fun changeLikeStatus(feedPost: FeedPost){
        viewModelScope.launch(exceptionHandler) {
            changeLikeStatusUseCase(feedPost)
        }
    }

    fun remove(feedPost: FeedPost){
        viewModelScope.launch(exceptionHandler) {
            deletePostUseCase(feedPost)
        }
    }

}