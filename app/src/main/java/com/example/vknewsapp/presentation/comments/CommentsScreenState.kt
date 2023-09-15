package com.example.vknewsapp.presentation.comments

import com.example.vknewsapp.domain.entity.FeedPost
import com.example.vknewsapp.domain.entity.PostComment

sealed class CommentsScreenState {

    object Initial: CommentsScreenState()

    object Loading: CommentsScreenState()

    data class Comments(val comments: List<PostComment>, val feedPost: FeedPost): CommentsScreenState()

}