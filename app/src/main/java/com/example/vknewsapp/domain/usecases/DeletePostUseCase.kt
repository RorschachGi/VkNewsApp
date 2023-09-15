package com.example.vknewsapp.domain.usecases

import com.example.vknewsapp.domain.entity.FeedPost
import com.example.vknewsapp.domain.repository.NewsFeedRepository

class DeletePostUseCase(
    private val repository: NewsFeedRepository
) {

    suspend operator fun invoke(feedPost: FeedPost){
        return repository.deletePost(feedPost)
    }

}