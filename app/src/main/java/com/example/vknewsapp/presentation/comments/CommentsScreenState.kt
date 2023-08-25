package com.example.vknewsapp.presentation.comments

import com.example.vknewsapp.domain.FeedPost
import com.example.vknewsapp.domain.PostComment

sealed class CommentsScreenState {

    object Initial: CommentsScreenState()

    data class Comments(val comments: List<PostComment>, val feedPost: FeedPost): CommentsScreenState()

}