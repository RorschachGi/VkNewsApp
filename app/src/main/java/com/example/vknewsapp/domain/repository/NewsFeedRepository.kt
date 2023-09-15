package com.example.vknewsapp.domain.repository


import com.example.vknewsapp.domain.entity.*
import kotlinx.coroutines.flow.*

interface NewsFeedRepository {

    fun getAuthStateFlow(): StateFlow<AuthState>

    fun getRecommendations(): StateFlow<List<FeedPost>>

    fun getComments(feedPost: FeedPost): Flow<List<PostComment>>

    suspend fun checkAuthState()

    suspend fun loadNextData()

    suspend fun deletePost(feedPost: FeedPost)

    suspend fun changeLikeStatus(feedPost: FeedPost)

}