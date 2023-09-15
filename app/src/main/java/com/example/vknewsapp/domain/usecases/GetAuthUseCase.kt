package com.example.vknewsapp.domain.usecases

import com.example.vknewsapp.domain.entity.AuthState
import com.example.vknewsapp.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow

class GetAuthUseCase(
    private val repository: NewsFeedRepository
) {

    operator fun invoke(): StateFlow<AuthState>{
        return repository.getAuthStateFlow()
    }

}