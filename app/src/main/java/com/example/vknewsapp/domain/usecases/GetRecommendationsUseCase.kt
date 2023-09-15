package com.example.vknewsapp.domain.usecases


import com.example.vknewsapp.domain.entity.FeedPost
import com.example.vknewsapp.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow

class GetRecommendationsUseCase(
    private val repository: NewsFeedRepository
) {

    operator fun invoke(): StateFlow<List<FeedPost>>{
        return repository.getRecommendations()
    }

}