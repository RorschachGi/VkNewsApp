package com.example.vknewsapp.presentation.news

import com.example.vknewsapp.domain.entity.FeedPost

sealed class NewsFeedScreenState {

    object Initial: NewsFeedScreenState()

    object Loading: NewsFeedScreenState()

    data class Posts(
        val posts: List<FeedPost>,
        val nextDataIsLoading: Boolean = false
    ): NewsFeedScreenState()

}