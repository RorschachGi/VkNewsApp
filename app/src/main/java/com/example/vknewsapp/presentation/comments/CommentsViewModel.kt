package com.example.vknewsapp.presentation.comments

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.vknewsapp.data.repository.NewsFeedRepositoryImpl
import com.example.vknewsapp.domain.entity.FeedPost
import com.example.vknewsapp.domain.usecases.GetCommentsUseCase
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart


class CommentsViewModel(
    feedPost: FeedPost,
    application: Application
): ViewModel() {

    private val repository = NewsFeedRepositoryImpl(application)

    private val getCommentsUseCase = GetCommentsUseCase(repository)

    val screenState = getCommentsUseCase(feedPost)
        .map { CommentsScreenState.Comments(
            comments = it,
            feedPost = feedPost
        ) as CommentsScreenState }
        .onStart { emit(CommentsScreenState.Loading) }

}