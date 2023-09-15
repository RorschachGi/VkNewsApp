package com.example.vknewsapp.domain.usecases

import com.example.vknewsapp.domain.entity.FeedPost
import com.example.vknewsapp.domain.entity.PostComment
import com.example.vknewsapp.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class GetCommentsUseCase(
    private val repository: NewsFeedRepository
) {

    operator fun invoke(feedPost: FeedPost): Flow<List<PostComment>> {
        return repository.getComments(feedPost)
    }

}